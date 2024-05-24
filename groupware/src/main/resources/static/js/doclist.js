/* doclist.js */

console.log('static/js/doclist.js');

const token = document.querySelector('meta[name="_csrf"]').getAttribute('content');
const header = document.querySelector('meta[name="_csrf_header"]').getAttribute('content');


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
