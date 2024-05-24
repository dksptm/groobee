/* doc.js */

console.log('static/js/doc.js');

const token = document.querySelector('meta[name="_csrf"]').getAttribute('content');
const header = document.querySelector('meta[name="_csrf_header"]').getAttribute('content');

let editor;

/* 템플릿 체인지 이벤트 : 등록 */
const tempChange = function() {

	// 안내메시지 숨기기.
	$('.cntn-slide').slideUp();
	// 휴가원,지출결의 숨기기
	$('#textArea').hide();
	$('#tempEXT').slideUp();
	$('#tempPTO').slideUp();
	$('div#pto').find('input, textarea').val('');
	
	// 옵션값 확인하기.
	let opt = $('#tempId option:selected');
	
	// 옵션값 선택안함
	if(opt.val() == 'no-data' || opt.val() == 'temp001') {
		if(editor) {
			editor.setData('');
		}
	}
	
	// 옵션값 선택안함/휴가원/지출결의
	if(opt.val() == 'no-data') {
		$('.cntn-slide').slideDown();
		return;
	}
	
	if(opt.val() == 'temp001') {
		$('#tempPTO').slideDown();
		ptoForm();
		return;
	}
	
	if(opt.val() == 'TP001') {
		$('#tempEXT').slideDown();
		return;
	}
	
	// 옵션값 그 외.
	$('#textArea').slideDown();
	if (!editor) {
        console.log('에디터 없음');
        ClassicEditor
            .create(document.querySelector('#cntn'))
            .then(newEditor => {
                editor = newEditor;
                getHtml();
            })
            .catch(error => {
                console.error(error);
            });
    } else {
    	console.log('에디터 있음');
    	editor.setData('');
        getHtml();
    }
    
}

/* 템플릿 체인지 이벤트 : 수정 */
const tempChangeUpdate = function() {

	// 안내메시지 숨기기.
	$('.cntn-slide').slideUp();
	// 휴가원,지출결의 숨기기
	$('#textArea').hide();
	$('#tempEXT').slideUp();
	$('#tempPTO').slideUp();
	$('div#pto').find('input, textarea').val('');
	
	// 옵션값 확인하기.
	let opt = $('#tempId option:selected');
	
	// 옵션값 선택안함
	if(opt.val() == 'no-data' || opt.val() == 'TP001' || opt.val() == 'TP002' ) {
		if(editor) {
			editor.setData('');
		}
	}
	
	// 옵션값 선택안함/휴가원/지출결의
	if(opt.val() == 'no-data') {
		$('.cntn-slide').slideDown();
		return;
	}
	
	if(opt.val() == 'TP002') {
		$('#tempPTO').slideDown();
		ptoForm();
		return;
	}
	
	if(opt.val() == 'TP001') {
		$('#tempEXT').slideDown();
		return;
	}
	
	// 옵션값 그 외.
	$('#textArea').show();
	$('#textArea').children('*').remove();
	$('#textArea').append($('<textarea name="cntn" id="cntn"></textarea>'));
	ClassicEditor
        .create(document.querySelector('#cntn'))
        .then(newEditor => {
            editor = newEditor;
            getHtml();
        })
        .catch(error => {
            console.error(error);
        });
            
}

/* 템플릿 체인지 이벤트 -> 템플릿경로 가져와서 세팅 */
const getHtml = function() {

	// 옵션값 그 외.
	let tempPath = $('#tempId option:selected').data('temp');

	// ajax로 템플릿 경로통해 가져오기.
	$.ajax({
		url : 'files/' + tempPath,
		dataType: "html",
		beforeSend: function(xhr) {
	        xhr.setRequestHeader(header, token); // 헤더에 CSRF 토큰 추가
	    },
	    success: function(html) {
	    	 editor.setData(html);
	    	 console.log(html);
	    },
	    error: function(err){
	    	console.log('ERR : ', err);
	    }
	})
	
}

/* 결재자 선택 모달 함수.*/
const getDocAprs = () => {
	let trs = $('#aprlist > tbody > tr');
	let area = $('#getAprs');
	let ctn = 0;
	
	area.children().remove();
	
	let drf = $('input[name="draft"]').val();
	let drfn = $('input[name="draftName"]').val();	
	area.append($(`<p>
					<input name="aprs[0].aprSq" type="text" value="1" readonly />번
					<input name="aprs[0].aprEmp" type="hidden" value="${drf}" readonly />
					<input name="aprs[0].aprName" type="text" value="${drfn}" readonly />
				   </p>`))
				   
	trs.each(function(idx,ele){
		let tr = $(ele);
		
			let creatTag = $(`<p>
							<input name="aprs[${(idx+1)}].aprSq" type="text"
							 value="${tr.find('td').eq(1).text()}" readonly />번
							<input name="aprs[${(idx+1)}].aprEmp" type="hidden"
							 value="${tr.data('emp')}" readonly />
							<input name="aprs[${(idx+1)}].aprName" type="text"
							 value="${tr.find('td').eq(2).text()}" readonly />
						   </p>`);
		
		area.append(creatTag);
		ctn++;
	});
	
	$('#finalLine').val(++ctn);
	$('.input-tag-css input').css('border','none').css('width','50px').css('background','transparent');
	
} // 결재자선택 end.

/* 참조자 선택 모달 함수.*/
const getDocRefs = () => {
	let trs = $('#refslist > tbody > tr');
	let area = $('#getRefs');
	
	area.children().remove();
	
	trs.each(function(idx,ele){
		let tr = $(ele);
		
		let creatTag = $(`<p>
							<input name="refs[${(idx)}]" type="hidden"
								   value="${tr.data('emp')}" readonly />
							<span>${tr.find('td').eq(1).text()}&ensp;
							      ${tr.find('td').eq(2).text()}&ensp;
							      ${tr.find('td').eq(3).text()}</span>
						  </p>`);
		area.append(creatTag);			
	})
	
}// 참조자선택 end.

/* 업무 -name부여 */
const inTaskName = () => {

	let inputsNo = $('#getTasks').find('input[name="no"]');
	inputsNo.each(function(i,e) {
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

/* 휴가원 세팅*/
function ptoForm() {
	$('#sdt').val(getNow());
	$('#edt').val(getNow());
	$('td[data-td="name"]').text($('input[name="draftName"]').val());
	$('td[data-td="dept"]').text($('input[name="deptId"]').val());
	$('td[data-td="dno"]').text($('input[name="docNo"]').val());
	
}

/* 휴가원 등록 */
const ptoSubmit = () => {
	
	let ptoStartDt = $('#sdt').val() + ' ' + $('#stime').val();
	$('div#pto').append($('<input name="pto.ptoStartDt" />').val(ptoStartDt));
	let ptoEndDt = $('#edt').val() + ' ' + $('#etime').val()
	$('div#pto').append($('<input name="pto.ptoEndDt" />').val(ptoEndDt));

}

/* 날짜포맷(현재) */
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

/* 날짜포맷(지정) */
function getDate(data) {
	let date = new Date(data);
	let year = date.getFullYear();
	let month = ('0' + (date.getMonth()+1)).substr(-2);
	let day = ('0' + date.getDate()).substr(-2);
	
	let result = year + month + day;
	return result;
}//getDate()
