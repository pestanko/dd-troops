package cz.muni.fi.pa165.ddtroops.service.facade;

import cz.muni.fi.pa165.ddtroops.dto.RoleDTO;
import cz.muni.fi.pa165.ddtroops.dto.RoleUpdateDTO;
import cz.muni.fi.pa165.ddtroops.entity.Role;
import cz.muni.fi.pa165.ddtroops.facade.RoleFacade;
import cz.muni.fi.pa165.ddtroops.service.exceptions.DDTroopsServiceException;
import cz.muni.fi.pa165.ddtroops.service.services.BeanMappingService;
import cz.muni.fi.pa165.ddtroops.service.services.RoleService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Collection;

/**
 * Created by Petr Koláček
 *
 * @author Petr Koláček
 */
@Service
@Transactional
public class RoleFacadeImpl implements RoleFacade {
    private final Logger logger = LoggerFactory.getLogger(RoleFacadeImpl.class.getName());
    @Autowired
    private RoleService roleService;
    @Autowired
    private BeanMappingService beanMappingService;

    @Override
    public RoleDTO create(RoleDTO role) {
        Role roleEntity = beanMappingService.mapTo(role, Role.class);
        try {
            roleService.create(roleEntity);
        } catch (DDTroopsServiceException e) {
            logger.warn(e.getMessage(), e);
        }
        role.setId(roleEntity.getId());
        return role;
    }

    @Override
    public RoleDTO findById(long id) {
        Role role = null;
        try {
            role = roleService.findById(id);
        } catch (DDTroopsServiceException e) {
            logger.warn(e.getMessage(), e);
            return null;
        }
        return (role == null) ? null : beanMappingService.mapTo(role, RoleDTO.class);
    }

    @Override
    public RoleDTO findByName(String name) {
        Role role = null;
        try {
            role = roleService.findByName(name);
        } catch (DDTroopsServiceException e) {
            logger.warn(e.getMessage(), e);
            return null;
        }
        return (role == null) ? null : beanMappingService.mapTo(role, RoleDTO.class);
    }

    @Override
    public Collection<RoleDTO> findAll() {
        try {
            return beanMappingService.mapTo(roleService.findAll(), RoleDTO.class);
        } catch (DDTroopsServiceException e) {
            logger.warn(e.getMessage(), e);
        }
        return null;
    }

    @Override
    public RoleDTO update(RoleUpdateDTO role) {
        Role roleEntity = beanMappingService.mapTo(role, Role.class);
        try {
            Role r = roleService.update(roleEntity);
            r.setId(roleEntity.getId());
            return beanMappingService.mapTo(r, RoleDTO.class);
        } catch (DDTroopsServiceException ex) {
            logger.warn(ex.getMessage(), ex);
        }
        return null;
    }

    @Override
    public void delete(Long id) {
        if(id == null){
            throw new IllegalArgumentException("id");
        }
        Role roleEntity = beanMappingService.mapTo(findById(id), Role.class);
        try {
            roleService.delete(roleEntity);
        } catch (DDTroopsServiceException ex) {
            logger.warn(ex.getMessage(), ex);
        }
    }


}
