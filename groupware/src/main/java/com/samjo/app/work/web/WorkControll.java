package com.samjo.app.work.web;



import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.samjo.app.work.service.WorkManagerSearchVO;
import com.samjo.app.work.service.WorkManagerVO;
import com.samjo.app.work.service.WorkPageDTO;
import com.samjo.app.work.service.WorkSearchVO;
import com.samjo.app.work.service.WorkService;
import com.samjo.app.work.service.WorkVO;

@Controller
public class WorkControll {

	@Autowired
	WorkService workService;
	
	@GetMapping("worklists")
	public String selectlist(@PathVariable String empId, WorkSearchVO worksearchVO, Model model) {
		
		if (worksearchVO.getPage() == 0) {
			worksearchVO.setPage(1);
		}
		List<WorkVO> list = workService.selectlist(worksearchVO);
		model.addAttribute("list", list);
		WorkPageDTO workpageDTO = new WorkPageDTO(worksearchVO.getPage(), workService.workcount(worksearchVO));
		model.addAttribute("filter", workpageDTO);
		return "work/worklists";
		
	}

	// 일반 페이지 전체 조회
	@GetMapping("worklist")
	public String workList(WorkSearchVO worksearchVO, Model model) {
		 
		if (worksearchVO.getPage() == 0) {
			worksearchVO.setPage(1);
		}
		List<WorkVO> list = workService.workList(worksearchVO);
		model.addAttribute("list", list);
		WorkPageDTO workpageDTO = new WorkPageDTO(worksearchVO.getPage(), workService.workcount(worksearchVO));
		model.addAttribute("filter", workpageDTO);
		return "work/worklist";
	}

	// 관리자 페이지 전체 조회
	@GetMapping("workmanager")
	public String managerWorkList(WorkManagerSearchVO workmanagersearchVO, Model model) {
		if (workmanagersearchVO.getFilter() == null) {
			workmanagersearchVO.setFilter("dept_id");
		}
		if (workmanagersearchVO.getPage() == 0) {
			workmanagersearchVO.setPage(1);
		}
		List<WorkManagerVO> list = workService.managerWorkList(workmanagersearchVO);
		model.addAttribute("list", list);
		WorkPageDTO workpageDTO = new WorkPageDTO(workmanagersearchVO.getPage(), workService.managercount());
		model.addAttribute("filter", workpageDTO);
		return "work/workmanager";
	}

	// 상세페이지
	@GetMapping("workinfo")
	public String selectwork(WorkVO workVO, Model model) {
		WorkVO work = workService.selectWork(workVO);
		model.addAttribute("info", work);
		return "work/workinfo";
	}

	/*
	 * // 등록 처리(오라클에서 스케쥴러로 작동)
	 * 
	 * @RequestMapping(value = "worklist", method = RequestMethod.POST) public
	 * String insertwork(WorkVO workVO) { return "work/workinsert"; }
	 */	

	// 수정처리화면
	@GetMapping("workupdate")
	public String updatework(WorkVO workVO, Model model) {
		WorkVO work = workService.selectWork(workVO);
		model.addAttribute("info", work);
		return "work/Workupdate";
	}
	
	// 수정 처리
	@PostMapping("workupdate")
	public String update(WorkVO workVO) {
		workService.update(workVO);
		return "redirect:/worklist";
	}

	
	  // 출근 업데이트 처리(최초 한번만 업데이트)
	  // request.getRemoteAddr();(ipcheck)
		  @PostMapping("workin")
		  @ResponseBody
		  public Map<String, Object> workin(WorkVO workVO) {
			  return workService.workin(workVO);
		  }
		  
		  // 퇴근 업데이트(최초 이후 계속 업데이트)
		  @PostMapping("workout")
		  @ResponseBody
		  public Map<String, Object> workout(WorkVO workVO) {
			  return workService.workout(workVO);
		  }
		  
 		  // 조퇴 업데이트(최초 이후 계속 업데이트)
		  @PostMapping("workstop")
		  @ResponseBody
		  public Map<String, Object> workstop(WorkVO workVO) {
			  return workService.workstop(workVO);
		  }
		 

}
