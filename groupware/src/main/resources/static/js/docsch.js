/* doc.js */

console.log('static/js/docsch.js');

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
