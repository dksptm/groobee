/* doclist.js */

console.log('static/js/doclist.js');

const token = document.querySelector('meta[name="_csrf"]').getAttribute('content');
const header = document.querySelector('meta[name="_csrf_header"]').getAttribute('content');

/*  */
const tempChange = () => {

	//검색 AJAX 함수
	function showTable(page){
		
		let keywordCondition;
		let keyword;
		
		let aprStatCondition;		
		
		let dateCondition;
		let dtStart = $('.fromDt').val();
		let dtEnd = $('.toDt').val();
		
		let sortCondition;
		
		//키워드 검색 처리
		if($('select[name="keywordCondition"]').val()=='titile'){
			
			title = $('.searchKey').val();
			
		} else if($('.searchKeySelect').val()=='cntn'){
			
			cntn = $('.searchKey').val();
		
		} 
		
		//기간 검색 처리
		if($('.searchKeySelect').val()=='custname'){
		
			ctName = $('.searchKey').val();
			
		}else if($('.searchKeySelect').val()=='custno'){
			custNo = $('.searchKey').val();
		}
		
		//계약상태 검색처리
		if($('.ctStat').val()!='statAll'){
			ctStat = $('.ctStat').val();
		}
		//정렬 처리
		let ctSort = $('.searchSelect').val();
		
		$.ajax({
			url : '/sol/viewCtList',
			type : 'POST',
			beforeSend : function(xhr) {
				xhr.setRequestHeader(header, token);
			},
			data : {
				page : page,
				ctName : ctName,
				ctSort : ctSort,
				custNo : custNo,
				ctNo : ctNo,
				ctStart : ctStart,
				ctEnd : ctEnd,
				ctStat : ctStat,
				ctDt : ctDt
			},
			dataType : 'Text',
			success : function(result) {
				console.log(result);
				$('#ctTable').replaceWith(result);
			}
		});
	}
	
    
}

/* 업무 name부여 */
const schTaskName = () => {
	$('input.aleadyInputs').remove();

	let inputsNo = $('#getTasks').find('input[name="no"]');
	inputsNo.each(function(i,e) {
		e.setAttribute('name','schTaskNo[' + i + ']');
	});
	
	let inputsNa = $('#getTasks').find('input[name="na"]');
	inputsNa.each(function(i,e) {
		e.setAttribute('name','schTaskName[' + i + ']');
	});
	
}
