package cz.muni.fi.pa165.ddtroops.mvc.controllers;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;

import cz.muni.fi.pa165.ddtroops.dto.RoleDTO;
import cz.muni.fi.pa165.ddtroops.facade.RoleFacade;
import cz.muni.fi.pa165.ddtroops.mvc.Tools;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import org.springframework.web.util.UriComponentsBuilder;


@Controller
@RequestMapping("/roles")
public class RoleController {
    private final static Logger log = LoggerFactory.getLogger(RoleController.class);

    @Autowired
    RoleFacade roleFacade;

    @RequestMapping(value="", method = RequestMethod.GET)
    public String list(Model model, HttpServletRequest request, UriComponentsBuilder uriBuilder) {
        log.debug("List all");

        model.addAttribute("roles", roleFacade.findAll());
        return "roles/list";
    }


    @RequestMapping(value = "/read/{id}", method = RequestMethod.GET)
    public String read(@PathVariable long id, Model model, UriComponentsBuilder uriBuilder, HttpServletRequest request) {
        log.debug(" Read ({})", id);

        model.addAttribute("role", roleFacade.findById(id));
        return "roles/read";
    }

    @RequestMapping(value = "/delete/{id}", method = RequestMethod.POST)
    public String delete(@PathVariable long id, Model model, HttpServletRequest request, UriComponentsBuilder uriBuilder, RedirectAttributes redirectAttributes) {

        String res = Tools.redirectNonAdmin(request, uriBuilder, redirectAttributes);
        if(res != null) return res;

        RoleDTO role = roleFacade.findById(id);
        roleFacade.delete(id);
        log.debug("delete role({})", id);
        redirectAttributes.addFlashAttribute("alert_success", "Role \"" + role.getName() + "\" was deleted.");
        return "redirect:" + uriBuilder.path("/roles").build().toUriString();
    }
    
    @RequestMapping(value = "/edit/{id}", method = RequestMethod.GET)
    public String editRole(@PathVariable long id, Model model, HttpServletRequest request, RedirectAttributes redirectAttributes, UriComponentsBuilder uriBuilder) {
        String res = Tools.redirectNonAdmin(request, uriBuilder, redirectAttributes);
        if(res != null) return res;
        log.debug("[ROLE] Edit {}", id);
        RoleDTO roleDTO = roleFacade.findById(id);
        model.addAttribute("roleEdit", roleDTO);
        return "/roles/edit";
    }
    
    @RequestMapping(value="/edit/{id}", method = RequestMethod.POST)
    public String update(@PathVariable long id,
                          @Valid @ModelAttribute("roleEdit")RoleDTO formBean,
                          BindingResult bindingResult,
                          Model model,
                          UriComponentsBuilder uriBuilder,
                          RedirectAttributes redirectAttributes,
                          HttpServletRequest request) {

        String res = Tools.redirectNonAdmin(request, uriBuilder, redirectAttributes);
        if(res != null) return res;

        formBean.setId(id);

        if (roleFacade.findByName(formBean.getName()) != null) {
             redirectAttributes.addFlashAttribute("alert_warning", "Role with name " + formBean.getName() + " already exists");
             return "redirect:" + uriBuilder.path("/roles/edit/{id}").buildAndExpand(id).encode().toUriString();
        }
        
        if (bindingResult.hasErrors()) {
            for (ObjectError ge : bindingResult.getGlobalErrors()) {
                log.trace("ObjectError: {}", ge);
            }
            for (FieldError fe : bindingResult.getFieldErrors()) {
                model.addAttribute(fe.getField() + "_error", true);
                log.trace("FieldError: {}", fe);
            }

            model.addAttribute("roleEdit", formBean);
            return "/roles/edit";
        }

        log.debug("[ROLE] Update: {}", formBean);
        RoleDTO result = roleFacade.update(formBean);

        redirectAttributes.addFlashAttribute("alert_success", "Role " + result.getName() + " was updated");
         return "redirect:" + uriBuilder.path("/roles/read/{id}").buildAndExpand(id).encode().toUriString();
    }
    
        
    @RequestMapping(value = "/create", method = RequestMethod.GET)
    public String createRole(Model model,  HttpServletRequest request, UriComponentsBuilder uriBuilder, RedirectAttributes redirectAttributes) {
        String res = Tools.redirectNonAdmin(request, uriBuilder, redirectAttributes);
        if(res != null) return res;
        log.debug("[ROLE] Create");
        model.addAttribute("roleCreate", new RoleDTO());
        return "/roles/create";
    }

    
    @RequestMapping(value = "/create", method = RequestMethod.POST)
    public String create(@Valid @ModelAttribute("roleCreate") RoleDTO formBean, BindingResult bindingResult,
                            Model model, RedirectAttributes redirectAttributes, UriComponentsBuilder uriBuilder, HttpServletRequest request) {

        String res = Tools.redirectNonAdmin(request, uriBuilder, redirectAttributes);
        if(res != null) return res;

        if (roleFacade.findByName(formBean.getName()) != null) {
             redirectAttributes.addFlashAttribute("alert_warning", "Role with name " + formBean.getName() + " already exists");
             return "redirect:" + uriBuilder.path("/roles/create").build().encode().toUriString();
        }
        
        log.debug("Create Role {})", formBean);

        if (bindingResult.hasErrors()) {
            for (ObjectError ge : bindingResult.getGlobalErrors()) {
                log.debug("ObjectError: {}", ge);
            }
            for (FieldError fe : bindingResult.getFieldErrors()) {
                model.addAttribute(fe.getField() + "_error", true);
                log.debug("FieldError: {}", fe);
            }

            model.addAttribute("roleCreate", formBean);
            return "/roles/create";
        }

        RoleDTO role = roleFacade.create(formBean);
        redirectAttributes.addFlashAttribute("alert_success", "Creation of " + role.getName() + " succeeded");
     
        return "redirect:" + uriBuilder.path("/roles").build().toUriString();
        
    }
    
}
