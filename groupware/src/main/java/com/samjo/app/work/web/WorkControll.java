package com.samjo.app.work.web;



import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
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
	// 관리자 페이지에서 진입 list
	@GetMapping("work/worklists")
	public String selectlist(WorkSearchVO worksearchVO, Model model, WorkVO workVO) {
		
		if (worksearchVO.getPage() == 0) {
			worksearchVO.setPage(1);
		}
		List<WorkVO> list = workService.selectlist(worksearchVO);
		model.addAttribute("list", list);
		WorkPageDTO workpageDTO = new WorkPageDTO(worksearchVO.getPage(), workService.workcount(worksearchVO));
		model.addAttribute("filter", workpageDTO);
		WorkVO emp = workService.selectemp(workVO);
		model.addAttribute("emp", emp);
		return "work/worklists";
		
	}
	
	  // 관리자 페이지에서 ajax
		@PostMapping("work/worklistfilters")
		public String workfilters(WorkSearchVO worksearchVO, Model model) {
			if (worksearchVO.getPage() == 0) {
				worksearchVO.setPage(1);
			}
			List<WorkVO> list = workService.workList(worksearchVO);
			model.addAttribute("list", list);
			WorkPageDTO workpageDTO = new WorkPageDTO(worksearchVO.getPage(), workService.workcount(worksearchVO));
			model.addAttribute("filter", workpageDTO);
			return "work/worklists :: #workTable";
		}

	// 일반 페이지 전체 조회
	@GetMapping("work/worklist")
	public String workList(WorkSearchVO worksearchVO, Model model, WorkVO workVO) {
		if (worksearchVO.getPage() == 0) {
			worksearchVO.setPage(1);
		}
		List<WorkVO> list = workService.workList(worksearchVO);
		model.addAttribute("list", list);
		WorkPageDTO workpageDTO = new WorkPageDTO(worksearchVO.getPage(), workService.workcount(worksearchVO));
		model.addAttribute("filter", workpageDTO);
		WorkVO emp = workService.selectemp(workVO);
		model.addAttribute("emp", emp);
		return "work/worklist";
	}
	
	// 일반 페이지 ajax
	@PostMapping("work/worklistfilter")
	public String workfilter(WorkSearchVO worksearchVO, Model model) {
		if (worksearchVO.getPage() == 0) {
			worksearchVO.setPage(1);
		}
		List<WorkVO> list = workService.workList(worksearchVO);
		model.addAttribute("list", list);
		WorkPageDTO workpageDTO = new WorkPageDTO(worksearchVO.getPage(), workService.workcount(worksearchVO));
		model.addAttribute("filter", workpageDTO);
		return "work/worklist :: #workTable";
	}

	// 관리자 페이지 전체 조회
	@GetMapping("work/workmanager")
	public String managerWorkList(WorkManagerSearchVO workmanagersearchVO, Model model, WorkVO workVO) {
		if (workmanagersearchVO.getPage() == 0) {
			workmanagersearchVO.setPage(1);
		}
		List<WorkManagerVO> list = workService.managerWorkList(workmanagersearchVO);
		model.addAttribute("list", list);
		WorkPageDTO workpageDTO = new WorkPageDTO(workmanagersearchVO.getPage(), workService.managercount());
		model.addAttribute("filter", workpageDTO);
		List<WorkVO> ipinlist = workService.selectinip(workVO);
		model.addAttribute("iplist", ipinlist);
		List<WorkVO> ipoutlist = workService.selectoutip(workVO);
		model.addAttribute("ipoutlist", ipoutlist);
		return "work/workmanager";
	}
	
	// 관리자 페이지 Ajax
		@PostMapping("work/workmanagersorting")
		public String managerWork(WorkManagerSearchVO workmanagersearchVO, Model model) {
			if (workmanagersearchVO.getPage() == 0) {
				workmanagersearchVO.setPage(1);
			}
			List<WorkManagerVO> list = workService.managerWorkList(workmanagersearchVO);
			model.addAttribute("list", list);
			WorkPageDTO workpageDTO = new WorkPageDTO(workmanagersearchVO.getPage(), workService.managercount());
			model.addAttribute("filter", workpageDTO);
			return "work/workmanager :: #sortingTable";
		}

	// 상세페이지
	@GetMapping("work/workinfo")
	public String selectwork(WorkVO workVO, Model model) {
		WorkVO work = workService.selectWork(workVO);
		model.addAttribute("info", work);
		return "work/workinfo";
	}

	// 수정처리화면
	@GetMapping("work/workupdate")
	public String updatework(WorkVO workVO, Model model) {
		WorkVO work = workService.selectWork(workVO);
		model.addAttribute("info", work);
		return "work/Workupdate";
	}
	
	// 수정 처리
	@PostMapping("work/workupdate")
	public String update(WorkVO workVO) {
		workService.update(workVO);
		return "redirect:/work/worklist?empId="+workVO.getEmpId();
	}

	
	  // 출근 업데이트 처리(최초 한번만 업데이트)
	  @PostMapping("work/workin")
	  @ResponseBody
	  public int workin(WorkVO workVO) {
		  return workService.workin(workVO);
	  }
	  
	  // 퇴근 업데이트(최초 이후 계속 업데이트)
	  @PostMapping("work/workout")
	  @ResponseBody
	  public Map<String, Object> workout(WorkVO workVO) {
		  return workService.workout(workVO);
	  }
	  
	  // 조퇴 업데이트(최초 이후 계속 업데이트)
	  @PostMapping("work/workstop")
	  @ResponseBody
	  public Map<String, Object> workstop(WorkVO workVO) {
		  return workService.workstop(workVO);
	  }
	  
	// ip 관리
	  
	// inip 등록
	@PostMapping("work/workinip")
	@ResponseBody
	public int insertinip(WorkVO workVO) {
		return workService.insertinip(workVO);
	}
		  
	// inip 등록
	@PostMapping("work/workoutip")
	@ResponseBody
	public int insertoutip(WorkVO workVO) {
		return workService.insertoutip(workVO);
	}
	
	@DeleteMapping("work/inipdelete")
	@ResponseBody
	public Map<String, Object> inipdelete(WorkVO workVO) {
		return null;
	}
	
	

}
