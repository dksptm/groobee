/* approval.js */

console.log('test.js');

$(function(){

	// 테이블 꾸미기
	$('.tab-content th').addClass('table-secondary');

	// custTemp, cntn 숨기기
	$('div#temparea').hide();
	$('textarea#cntn').hide();

	// 템플릿 내용 표시.
	let temp = document.querySelector('td[data-temp]').dataset.temp;
	console.log(temp)
	
	if(temp == 'TP001') {
		$('div#tempEXT').show();
	} else if(temp == 'TP002') {
		$('div#tempPTO').show();
	} else if (temp == 'TP003') {
		$('#textArea').show();
	} else {
		let tempPath = $('textarea#cntn').val();
		console.log(tempPath);
		$('#temparea').show();
		$.ajax({
			url : 'files/' + tempPath,
			dataType: "html",
			success: function(html) {
				$('#custTemp').html(html);
			},
			error: function(err){
				console.log('ERR : ', err);
			}
		})
	}



})


