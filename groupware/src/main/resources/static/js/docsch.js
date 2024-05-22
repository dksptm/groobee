/* doc.js */

console.log('static/js/docsch.js');

/* name부여 */
const schTaskName = () => {

	let inputs = $('#getTasks').find('input');
	inputs.each(function(i,e) {
		e.setAttribute('name','schTaskNo[' + i + ']');
	});
	
}
