package cz.muni.fi.pa165.ddtroops.service.facade;

import java.util.Collection;
import java.util.Set;
import java.util.stream.Collectors;

import cz.muni.fi.pa165.ddtroops.dto.HeroDTO;
import cz.muni.fi.pa165.ddtroops.dto.RoleDTO;
import cz.muni.fi.pa165.ddtroops.entity.Hero;
import cz.muni.fi.pa165.ddtroops.facade.HeroFacade;
import cz.muni.fi.pa165.ddtroops.service.exceptions.DDTroopsServiceException;
import cz.muni.fi.pa165.ddtroops.service.services.BeanMappingService;
import cz.muni.fi.pa165.ddtroops.service.services.HeroService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Created by Peter Zaoral.
 *
 * @author Peter Zaoral.
 */
@Service
@Transactional
public class HeroFacadeImpl implements HeroFacade {

    @Autowired
    private HeroService heroService;

    @Autowired
    private BeanMappingService beanMappingService;

    private Logger logger = LoggerFactory.getLogger(HeroFacadeImpl.class.getName());

    @Override
    public HeroDTO create(HeroDTO hero) {
        Hero heroEntity = beanMappingService.mapTo(hero, Hero.class);
        try {
            heroService.create(heroEntity);
        } catch (DDTroopsServiceException e) {
            logger.warn(e.getMessage(), e);
        }
        hero.setId(heroEntity.getId());
        return hero;
    }

    @Override
    public HeroDTO findById(long id) {

        Hero hero;
        try {
            hero = heroService.findById(id);
        } catch (DDTroopsServiceException e) {
            logger.warn(e.getMessage(), e);
            return null;
        }
        return (hero == null) ? null : beanMappingService.mapTo(hero, HeroDTO.class);
    }

    @Override
    public HeroDTO findByName(String name) {
        Hero hero;
        try {
            hero = heroService.findByName(name);
        } catch (DDTroopsServiceException e) {
            logger.warn(e.getMessage(), e);
            return null;
        }
        return (hero == null) ? null : beanMappingService.mapTo(hero, HeroDTO.class);
    }

    @Override
    public Collection<HeroDTO> findAll() {
        try {
            return beanMappingService.mapTo(heroService.findAll(), HeroDTO.class);
        } catch (DDTroopsServiceException e) {
            logger.warn(e.getMessage(), e);
        }
        return null;
    }

    @Override
    public HeroDTO update(HeroDTO hero) {
        Hero heroEntity = beanMappingService.mapTo(hero, Hero.class);
        try {

            Hero u = heroService.updateHero(heroEntity);
            hero.setId(heroEntity.getId());
            return beanMappingService.mapTo(u, HeroDTO.class);
        } catch (DDTroopsServiceException ex) {
            logger.warn(ex.getMessage(), ex);
        }
        return null;
    }

    @Override
    public Boolean delete(Long id) {
        if(id == null){
            throw new IllegalArgumentException("id");
        }
        Hero heroEntity = beanMappingService.mapTo(findById(id), Hero.class);
        try {
            heroService.deleteHero(heroEntity);
            return true;
        } catch (DDTroopsServiceException ex) {
            logger.warn(ex.getMessage(), ex);
        }
        return false;
    }

    @Override
    public Boolean deleteAll() {
        try {
            beanMappingService.mapTo(heroService.deleteAllHeroes(), HeroDTO.class);
            return true;
        } catch (DDTroopsServiceException e) {
            logger.warn(e.getMessage(), e);
        }
        return false;
    }

    @Override
    public HeroDTO addRole(final Long heroId, final Long roleId) {
        Hero hero = heroService.addRole(heroId, roleId);
        return beanMappingService.mapTo(hero, HeroDTO.class);
    }

    @Override
    public HeroDTO removeRole(final Long heroId, final Long roleId) {
        Hero hero = heroService.removeRole(heroId, roleId);
        return beanMappingService.mapTo(hero, HeroDTO.class);

    }

    @Override
    public Set<RoleDTO> heroRoles(final Long heroId) {
        return heroService.getRoles(heroId)
            .stream()
            .map((h) -> beanMappingService.mapTo(h, RoleDTO.class))
            .collect(Collectors.toSet());
    }


}