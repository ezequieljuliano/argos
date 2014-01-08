/*
 * Copyright 2013 Ezequiel Juliano Müller - ezequieljuliano@gmail.com.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package br.com.ezequieljuliano.argos.view;

import br.com.ezequieljuliano.argos.business.LoggerBC;
import br.com.ezequieljuliano.argos.domain.groupoperation.LoggerEntityCount;
import br.com.ezequieljuliano.argos.domain.groupoperation.LoggerHostCount;
import br.com.ezequieljuliano.argos.domain.groupoperation.LoggerLevelCount;
import br.com.ezequieljuliano.argos.domain.groupoperation.LoggerMarkerCount;
import br.com.ezequieljuliano.argos.domain.groupoperation.LoggerOccurrenceCount;
import br.com.ezequieljuliano.argos.domain.groupoperation.LoggerOwnerCount;
import br.com.ezequieljuliano.argos.security.AppCredential;
import br.com.ezequieljuliano.argos.util.DateUtil;
import br.gov.frameworkdemoiselle.message.MessageContext;
import br.gov.frameworkdemoiselle.stereotype.ViewController;
import java.io.Serializable;
import java.util.Date;
import java.util.List;
import javax.annotation.PostConstruct;
import javax.inject.Inject;
import org.primefaces.model.chart.CartesianChartModel;
import org.primefaces.model.chart.ChartSeries;
import org.primefaces.model.chart.LineChartSeries;
import org.primefaces.model.chart.PieChartModel;

@ViewController
public class DashboardMB implements Serializable {

    @Inject
    private MessageContext messageContext;

    @Inject
    private AppCredential credential;

    @Inject
    private LoggerBC loggerBC;

    private PieChartModel pieChartMarker;
    private PieChartModel pieChartLevel;
    private PieChartModel pieChartEntity;
    private PieChartModel pieChartHost;
    private PieChartModel pieChartOwner;
    private CartesianChartModel cartesianChartEvolution;
    private Date startDate = DateUtil.decreaseDaysFrom(new Date(), 5);
    private Date endDate = new Date();
    private int cartesianMaxY = 100;

    private void createPieChartMarker() {
        pieChartMarker = new PieChartModel();
        List<LoggerMarkerCount> counts = loggerBC.groupByMarkerUsingUserAndOccurrence(credential.getUser(), getStartDate(), getEndDate());
        for (LoggerMarkerCount obj : counts) {
            pieChartMarker.set(obj.getMarker().getName(), obj.getCount());
        }
    }

    private void createPieChartLevel() {
        pieChartLevel = new PieChartModel();
        List<LoggerLevelCount> counts = loggerBC.groupByLevelUsingUserAndOccurrence(credential.getUser(), getStartDate(), getEndDate());
        for (LoggerLevelCount obj : counts) {
            pieChartLevel.set(obj.getLevel().getName(), obj.getCount());
        }
    }

    private void createPieChartEntity() {
        pieChartEntity = new PieChartModel();
        List<LoggerEntityCount> counts = loggerBC.groupByEntityUsingUserAndOccurrence(credential.getUser(), getStartDate(), getEndDate());
        for (LoggerEntityCount obj : counts) {
            pieChartEntity.set(obj.getEntity().getName(), obj.getCount());
        }
    }

    private void createPieChartHost() {
        pieChartHost = new PieChartModel();
        List<LoggerHostCount> counts = loggerBC.groupByHostUsingUserAndOccurrence(credential.getUser(), getStartDate(), getEndDate());
        for (LoggerHostCount obj : counts) {
            pieChartHost.set(obj.getHost(), obj.getCount());
        }
    }

    private void createPieChartOwner() {
        pieChartOwner = new PieChartModel();
        List<LoggerOwnerCount> counts = loggerBC.groupByOwnerUsingUserAndOccurrence(credential.getUser(), getStartDate(), getEndDate());
        for (LoggerOwnerCount obj : counts) {
            pieChartOwner.set(obj.getOwner(), obj.getCount());
        }
    }

    private void createCartesianChartEvolution() {
        cartesianChartEvolution = new CartesianChartModel();
        ChartSeries series = new LineChartSeries();
        series.setLabel("Logs");
        List<LoggerOccurrenceCount> counts = loggerBC.groupByOccurrenceUsingUserAndOccurrence(credential.getUser(), getStartDate(), getEndDate());
        int i = 0;
        for (LoggerOccurrenceCount obj : counts) {
            i++;
            series.set("T" + i, obj.getCount());
        }
        cartesianChartEvolution.addSeries(series);
        cartesianMaxY = counts.size();
    }

    public void generateDashboard() {
        createPieChartMarker();
        createPieChartLevel();
        createPieChartEntity();
        createPieChartHost();
        createPieChartOwner();
        createCartesianChartEvolution();
    }

    @PostConstruct
    public void initialize() {
        generateDashboard();
    }

    public PieChartModel getPieChartMarker() {
        return pieChartMarker;
    }

    public PieChartModel getPieChartLevel() {
        return pieChartLevel;
    }

    public PieChartModel getPieChartEntity() {
        return pieChartEntity;
    }

    public PieChartModel getPieChartHost() {
        return pieChartHost;
    }

    public PieChartModel getPieChartOwner() {
        return pieChartOwner;
    }

    public CartesianChartModel getCartesianChartEvolution() {
        return cartesianChartEvolution;
    }

    public int getCartesianMaxY() {
        return cartesianMaxY;
    }

    public Date getStartDate() {
        return startDate;
    }

    public void setStartDate(Date startDate) {
        this.startDate = startDate;
    }

    public Date getEndDate() {
        return endDate;
    }

    public void setEndDate(Date endDate) {
        this.endDate = endDate;
    }

}
