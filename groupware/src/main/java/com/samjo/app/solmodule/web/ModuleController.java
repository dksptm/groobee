package com.samjo.app.solmodule.web;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

import com.samjo.app.solmodule.service.ModuleService;
import com.samjo.app.solmodule.service.ModuleVO;

@Controller
public class ModuleController {

	ModuleService moduleservice;

	@Autowired
	public ModuleController(ModuleService moduleservice) {
		this.moduleservice = moduleservice;
	}
	
	// 모듈 전체조회
	@GetMapping("solModList")
	public String moddulePage(Model model) {
		List<ModuleVO> list = moduleservice.modList();
		model.addAttribute("list", list);
		return "solution/module/moduleList";
	}

	// 모듈 등록
	@GetMapping("insertSolMod")
	public String InsertMod() {
		
		return "solution/module/insertModule";
	}

	// 템플릿 전체조회
	@GetMapping("solTempList")
	public String tempList() {
		return "solution/module/templateList";
	}

	// 템플릿 등록
	@GetMapping("insertSolTemp")
	public String InsertTemplate() {
		return "solution/module/insertTemp";
	}
}
