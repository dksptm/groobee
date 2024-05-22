/* doc.js */

console.log('static/js/doc.js');

const token = document.querySelector('meta[name="_csrf"]').getAttribute('content');
const header = document.querySelector('meta[name="_csrf_header"]').getAttribute('content');

let editor;

/* 템플릿 체인지 이벤트 */
const tempChange = function() {

	// 안내메시지 숨기기.
	$('.cntn-slide').slideUp();
	// 휴가원,지출결의 숨기기
	$('#tempPTO').hide();
	$('div#pto').find('input, textarea').val('');
	$('#tempEXT').hide();
	$('#textArea').hide();
	
	// 옵션값 확인하기.
	let opt = $('#tempId option:selected');
	
	// 옵션값 선택안함/휴가원/지출결의
	if(opt.val() == 'no-data' || opt.val() == 'TP001' || opt.val() == 'TP002' ) {
		if(editor) {
			editor.setData('');
			$('#cntn').hide();
		}
	}
	
	// 옵션값 선택안함/휴가원/지출결의
	if(opt.val() == 'no-data') {
		$('.cntn-slide').slideDown();
		return;
	}
	
	if(opt.val() == 'TP002') {
		$('#tempPTO').show();
		ptoForm();
		return;
	}
	
	if(opt.val() == 'TP001') {
		$('#tempEXT').show();
		return;
	}
	
	// 옵션값 그 외.
	$('#textArea').show();
	if (!editor) {
        console.log('에디터 없음');
        ClassicEditor
            .create(document.querySelector('#cntn'))
            .then(newEditor => {
                console.log('에디터 준비', newEditor);
                editor = newEditor;
                getHtml();
            })
            .catch(error => {
                console.error(error);
            });
    } else {
    	editor.setData('');
        getHtml();
        console.log('에디터 이미 있음');
    }
    
    console.log('tempChange 끝');

}

/* 템플릿 체인지 이벤트 -> 템플릿경로 가져와서 세팅 */
const getHtml = function() {

	// 옵션값 그 외.
	let tempPath = $('#tempId option:selected').data('temp');
	console.log('tempPath', tempPath);
	// ajax로 템플릿 경로통해 가져오기.
	$.ajax({
		url : 'files/' + tempPath,
		dataType: "html",
		beforeSend: function(xhr) {
	        xhr.setRequestHeader(header, token); // 헤더에 CSRF 토큰 추가
	    },
	    success: function(html) {
	    	 editor.setData(html);
	    },
	    error: function(err){
	    	console.log('ERR : ', err);
	    }
	})
	
	console.log('getHtml 끝');
}

/* 결재자 선택 모달 함수.*/
const getDocAprs = () => {
	let trs = $('#aprlist > tbody > tr');
	let spanTag = $('#selaprs');
	spanTag.children().remove();
	let drf = $('input[name="draft"]').val();
	let drfn = $('td[data-draft]').text();
	spanTag.append($(`<p>
						<input name="aprs[0].aprSq" type="text"
						 value="1" readonly />번
						<input name="aprs[0].aprEmp" type="hidden"
						 value="${drf}" readonly />
						<input name="aprs[0].aprName" type="text"
						 value="${drfn}" readonly />
					   </p>`))
	let ctn = 0;
	
	trs.each(function(idx,tr){
		let get = $(tr);
		
		let creatInputTag = $(`<p>
								<input name="aprs[${(idx+1)}].aprSq" type="text"
								 value="${get.find('td').eq(1).text()}" readonly />번
								<input name="aprs[${(idx+1)}].aprEmp" type="hidden"
								 value="${get.data('emp')}" readonly />
								<input name="aprs[${(idx+1)}].aprName" type="text"
								 value="${get.find('td').eq(2).text()}" readonly />
							   </p>`);
		
		spanTag.append(creatInputTag);
		ctn++;
	});
	$('#finalLine').val(++ctn);
	$('.input-tag-css input').css('border','none').css('width','50px').css('background','transparent');
}

/* 참조자 선택 모달 함수.*/
const getDocRefs = () => {
	let trs = $('#refslist > tbody > tr');
	let divTag = $('#getRefs');
	divTag.children().remove();
	
	trs.each(function(idx,tr){
		let get = $(tr);
		
		let creatTag = $(`<p>
							<input name="refs[${(idx)}]" type="hidden"
								   value="${get.data('emp')}" readonly />
							<span>${get.find('td').eq(1).text()}
							      ${get.find('td').eq(2).text()}
							      ${get.find('td').eq(3).text()}</span>
						  </p>`);
		divTag.append(creatTag);			
	})
}

/* 관련업무 선택 모달 함수.*/
/*
const getDocTasks = () => {
	
	let trs = $('#list3 > tbody input[name=chk-list3]:checked').closest('tr');
	let divTag = $('#getTasks');
	divTag.children().remove();
	
	trs.each(function(idx,tr) {
		let get = $(tr);
		let data = get.data('task');
		let creatTag = $(`<span>
							<input name="tasks[${(idx)}]" type="hidden"
								   value="${get.data('task')}" readonly />
							<span>${get.find('td').eq(0).text()} 
							      ${get.find('td').eq(1).text()}</span>
					      	<span>X</span>
						  </span>&ensp;`);
		divTag.append(creatTag);
	});
	
}*/

const getDocTasks = () => {
	let inputs = $('#getTasks').find('input');
	inputs.each(function(i,e) {
		e.setAttribute('name','tasks[' + i + ']');
	});
}

/* 필수입력 항목선택.*/
function docCheck(e) {
	if($('#title').val() == '') {
		alert('제목은 필수입력 항목입니다.');
		$('#title').focus();
		return false;
	}
	if($('#tempId').val() == 'no-data') {
		alert('템플릿을 선택해주세요.');
		$('#tempId').focus();
		return false;
	}
	if($('#tempId').val() == 'TP002') {
		if($('#ptoType').val() == '' || $('#sdt').val() == '' || $('#edt').val() ==''){
			alert('휴가원은 휴가종류, 시작/종료날짜\n모두 선택해야 합니다.');
			return false;
		}
	}
	return true;
}

/* 휴가원 */
function ptoForm() {
	$('#sdt').val(getNow());
	$('#edt').val(getNow());
	$('td[data-td="name"]').text($('input[name="draftName"]').val());
	$('td[data-td="dept"]').text($('input[name="deptId"]').val());
	$('td[data-td="dno"]').text($('input[name="docNo"]').val());
		
	console.log('휴가원세팅끝');
	
}

/* 휴가원 등록 */
const ptoSubmit = () => {

	let ptoStartDt = $('#sdt').val() + ' ' + $('#stime').val();
	$('div#pto').append($('<input name="pto.ptoStartDt" />').val(ptoStartDt));
	let ptoEndDt = $('#edt').val() + ' ' + $('#etime').val()
	$('div#pto').append($('<input name="pto.ptoEndDt" />').val(ptoEndDt));
	
	/*
	$.ajax({
		url: 'pto',    
    	type: 'POST',
    	contentType : 'application/json',
    	beforeSend: function(xhr) {
    		xhr.setRequestHeader(header, token);
		},
    	data: JSON.stringify(data)
	})
	.done(response => {
    	resolve(response);
	})
	.fail(err => {
	    alert('휴가원 저장 중 오류');
	    reject(err);
	});
	*/
}

/* 날짜포맷 */
function getNow(){
	let now = new Date();
	let year = now.getFullYear();
	let month = now.getMonth() + 1;
	let day = now.getDate();
	
	month = month < 10 ? '0' + month : month;
    day = day < 10 ? '0' + day : day;
	
	let date = year + '-' + month + '-' + day;
	return date;
}


/* 파일저장 (FileInfoController) */
/*
const saveFile = (fileList, dno, eid) => { 
	// 아작스 결과를 프로미즈 객체 담기 
	return new Promise((resolve, reject) => {

	    let formData = new FormData(); 
	    
	    for(let file of fileList) {
	        formData.append('uploadFiles', file);
	    }
	    formData.append('empId', eid);
		formData.append('docNo', dno);	
			
	    $.ajax({
	        url: 'uploadFiles',    
	        type: 'POST',
	        processData: false, 
	        contentType: false,
	        beforeSend: function(xhr) {
	        	xhr.setRequestHeader(header, token);
	    	},
	        data: formData
	    })
	    .done(response => {
	        resolve(response);
	    })
	    .fail(err => {
	        alert('첨부파일 저장 중 오류');
	        reject(err);
	    });
	})
	console.log('프로미즈 객체 끝');
}
*/
