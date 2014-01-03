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
package br.com.ezequieljuliano.argos.service;

import br.com.ezequieljuliano.argos.business.EntityBC;
import br.com.ezequieljuliano.argos.business.LevelBC;
import br.com.ezequieljuliano.argos.business.LoggerBC;
import br.com.ezequieljuliano.argos.business.MarkerBC;
import br.com.ezequieljuliano.argos.business.UserBC;
import br.com.ezequieljuliano.argos.domain.Entity;
import br.com.ezequieljuliano.argos.domain.Level;
import br.com.ezequieljuliano.argos.domain.Logger;
import br.com.ezequieljuliano.argos.domain.Marker;
import br.com.ezequieljuliano.argos.domain.User;
import br.com.ezequieljuliano.argos.exception.ResponseServiceException;
import br.com.ezequieljuliano.argos.service.dto.LoggerDTO;
import br.com.ezequieljuliano.argos.service.dto.ResponseDTO;
import br.com.ezequieljuliano.argos.util.DateUtil;
import javax.inject.Inject;
import javax.ws.rs.Consumes;
import javax.ws.rs.HeaderParam;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

@Path("/logger")
public class LoggerService {

    @Inject
    private UserBC userBC;

    @Inject
    private LoggerBC loggerBC;

    @Inject
    private EntityBC entityBC;

    @Inject
    private LevelBC levelBC;

    @Inject
    private MarkerBC markerBC;

    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public ResponseDTO save(@HeaderParam("IdentifierKey") String identifierKey, LoggerDTO logger) {
        try {
            User user = findUser(identifierKey);
            Entity entity = findEntity(logger.getEntity().getExternalKey());
            Level level = findLevel(logger.getLevel().getName());
            Marker marker = findMarker(logger.getMarker().getName());

            Logger loggerSave = new Logger();
            loggerSave.setEntity(entity);
            loggerSave.setUser(user);
            loggerSave.setLevel(level);
            loggerSave.setMarker(marker);
            loggerSave.setHost(logger.getHost());
            loggerSave.setKeywords(logger.getKeywords());
            loggerSave.setMessage(logger.getMessage());
            loggerSave.setOccurrence(DateUtil.stringToDateTime(logger.getOccurrence()));
            loggerSave.setOwner(logger.getOwner());

            loggerBC.validateAndSave(loggerSave);
        } catch (ResponseServiceException ex) {
            return new ResponseDTO(ex.getResponse().getCode(), ex.getResponse().getReason());
        } catch (Exception e) {
            return new ResponseDTO(ResponseTypes.error.getCode(), ResponseTypes.error.getReason());
        }
        return new ResponseDTO(ResponseTypes.ok.getCode(), ResponseTypes.ok.getReason());
    }

    private User findUser(String identifierKey) throws ResponseServiceException {
        User user = userBC.findByIdentifierKey(identifierKey);
        if (user == null) {
            throw new ResponseServiceException(ResponseTypes.userNotFound);
        }
        if (!user.isActive()) {
            throw new ResponseServiceException(ResponseTypes.userInactive);
        }
        return user;
    }

    private Entity findEntity(String externalKey) throws ResponseServiceException {
        Entity entity = entityBC.findByExternalKey(externalKey);
        if (entity == null) {
            throw new ResponseServiceException(ResponseTypes.entityNotFound);
        }
        if (!entity.isActive()) {
            throw new ResponseServiceException(ResponseTypes.entityInactive);
        }
        return entity;
    }

    private Level findLevel(String name) throws ResponseServiceException {
        Level level = levelBC.findOneByName(name);
        if (level == null) {
            throw new ResponseServiceException(ResponseTypes.levelNotFound);
        }
        return level;
    }

    private Marker findMarker(String name) throws ResponseServiceException {
        Marker marker = markerBC.findOneByName(name);
        if (marker == null) {
            throw new ResponseServiceException(ResponseTypes.markerNotFound);
        }
        return marker;
    }
}
