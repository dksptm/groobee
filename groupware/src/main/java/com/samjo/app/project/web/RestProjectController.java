package com.samjo.app.project.web;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.samjo.app.project.service.ProjectService;
import com.samjo.app.project.service.ProjectVO;

@RestController
public class RestProjectController {
	/*@Autowired
    private ProjectService projectService;

    @PostMapping("coopUpdate")
    public Map<String, Object> coopUpdateProcessAjax(@RequestBody ProjectVO projectVO) {
        return projectService.coopUpdate(projectVO);
    }*/
}
