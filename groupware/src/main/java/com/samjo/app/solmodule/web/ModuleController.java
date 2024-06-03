package com.samjo.app.solmodule.web;

import java.io.FileOutputStream;
import java.util.HashMap;
import java.util.List;
import java.util.UUID;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.tomcat.util.codec.binary.Base64;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.samjo.app.approval.service.TempVO;
import com.samjo.app.common.service.SearchVO;
import com.samjo.app.cust.service.CustVO;
import com.samjo.app.solmodule.service.ModuleService;
import com.samjo.app.solmodule.service.ModuleVO;
import com.samjo.app.solmodule.service.TempDTO;
import com.samjo.app.upload.service.UploadService;

/*솔루션 - 모듈관리페이지 컨트롤러
 작성자 : 정연복 / 작성일자 : 240527 
    
*/
@Controller
public class ModuleController {
	@Autowired
	ModuleService moduleservice;
	@Autowired
	UploadService uploadservice;

	/**
	 * 모듈 전체조회
	 * @param model
	 * @return 
	 */
	@GetMapping("solModList")
	public String moddulePage(Model model) {
		List<ModuleVO> list = moduleservice.modList();
		model.addAttribute("list", list);
		return "solution/module/moduleList";
	}

	// 템플릿 전체조회
	/**
	 * @param searchVO
	 * @param model
	 * @return
	 */
	@GetMapping("solTempList")
	public String tempList(SearchVO searchVO, Model model) {
		if (searchVO.getPage() <= 0) {
			searchVO.setPage(1);
		}
		List<TempVO> list = moduleservice.tempList(searchVO);
		model.addAttribute("list", list);
		TempDTO tempDTO = new TempDTO(searchVO.getPage(), moduleservice.count());
		model.addAttribute("TempDTO", tempDTO);
		return "solution/module/templateList";
	}

	// 템플릿 상세 화면
	/**
	 * 
	 * @param tempNo
	 * @param model
	 * @return
	 */
	@GetMapping("solTempInfo/{tempNo}")
	public String tempInfo(@PathVariable String tempNo, Model model) {
		TempVO tempVO = moduleservice.tempInfo(tempNo);
		model.addAttribute("tempVO", tempVO);
		return "solution/module/templateInfo";
	}

	// 템플릿 등록 화면
	/**
	 * 
	 * @param tempVO
	 * @param model
	 * @return
	 */
	@GetMapping("insertSolTemp")
	public String InsertTemplate(TempVO tempVO, Model model) {
		List<CustVO> list = moduleservice.custList();
		model.addAttribute("custlist", list);
	
		return "solution/module/insertTemp";
	}
	
	// 템플릿 등록 처리
	/**
	 * 
	 * @param tempVO
	 * @param binaryData
	 * @return
	 */
	@PostMapping("insertSolTemp")
	@ResponseBody
	public String tempInsert(@ModelAttribute TempVO tempVO, @RequestParam("imgSrc") String binaryData) {
		tempVO.setTempImg(moduleservice.saveImg(binaryData));
		return moduleservice.tempInsert(tempVO);
	}

	// 템플릿 수정 화면
	/**
	 * 
	 * @param tempVO
	 * @param model
	 * @return
	 */
	@GetMapping("updateSolTemp")
	public String UpdateTemplate(@ModelAttribute TempVO tempVO, Model model) {
		List<CustVO> list = moduleservice.custList();
		model.addAttribute("custlist", list);
		tempVO = moduleservice.tempInfo(tempVO.getTempNo());
		model.addAttribute("tempVO", tempVO);
		return "solution/module/updateTemp";
	}

	// 템플릿 수정 처리
	/**
	 * 
	 * @param id
	 * @param tempVO
	 * @param model
	 * @return
	 */
	@PostMapping("tempUpdate/{id}")
	public String tempUpdate(@PathVariable String id, @ModelAttribute TempVO tempVO, Model model) {
		tempVO.setTempNo(id);
		moduleservice.tempUpdate(tempVO);
		return "redirect:/solTempInfo/" + id;
	}
	
	//템플릿 삭제 처리
	/**
	 * 
	 * @param id
	 * @return
	 */
	@GetMapping("tempDelete/{id}")
	public String tempDelete(@PathVariable String id) {
		TempVO tempVO = new TempVO();
		tempVO.setTempNo(id);
		moduleservice.tempDelete(tempVO);
		return "redirect:/solTempList";
	}

	// 미리보기 이미지 저장 샘플
	@ResponseBody
	@RequestMapping(value = { "ImgSaveTest" }, method = RequestMethod.POST)
	public ModelMap ImgSave(@RequestParam HashMap<Object, Object> param, final HttpServletRequest request,
			final HttpServletResponse response) throws Exception {
		ModelMap map = new ModelMap();

		String binaryData = request.getParameter("imgSrc");
		FileOutputStream stream = null;
		try {
			System.out.println("binary file   " + binaryData);
			if (binaryData == null || binaryData.trim().equals("")) {
				throw new Exception();
			}
			binaryData = binaryData.replaceAll("data:image/png;base64,", "");
			byte[] file = Base64.decodeBase64(binaryData);
			String fileName = UUID.randomUUID().toString() + "_test";

			stream = new FileOutputStream("C:/uploadTest2/" + fileName + ".png");
			stream.write(file);
			stream.close();
			System.out.println("캡처 저장");

		} catch (Exception e) {
			e.printStackTrace();
			System.out.println("에러 발생");
		} finally {
			if (stream != null) {
				stream.close();
			}
		}

		map.addAttribute("resultMap", "");
		return map;
	}
}
