/* doc.js */

console.log('static/js/docsch.js');

/* name부여 */
const schTaskName = () => {

	let inputs = $('#getTasks').find('input');
	inputs.each(function(i,e) {
		e.setAttribute('name','schTaskNo[' + i + ']');
	});
	
}

/* 검색버튼 */
const search = () => {

	let taskAry = [];
	let obj = {}

	let finds = $('#getTasks').find('input');
	finds.each((i,e) => {
		taskAry.push(e.value);
	})

	obj['schTaskNos'] = taskAry;
	console.log(obj);
	
	searchSVC.schTest(
		obj,
		resutl => {
		
			let tbody = $('tbody.resultList');
			let tr = tbody.children('tr').eq(0).clone();
			
			tbody.children('*').remove();
			
			let docs = []
			docs = resutl.list;
			
			for(doc of docs) {
			
				let copy = tr.clone();
				copy.attr('onclick','#');
				copy.children('*').remove();
				
				let date = getDate(doc.draftDt);
				
				let taskList = []
				taskList = doc.taskList;
				let str;
				for(task of taskList){
					str += `<em>${task.taskName}</em><br>`;
				}
				
				copy.append($(`<td>${doc.docNo}</td>
							   <td>${doc.deptName}</td>
							   <td>${doc.tempName}</td>
							   <td data-bs-toggle="tooltip" data-bs-offset="0,4" 
							   		data-bs-placement="bottom" data-bs-html="true" 
							   		title="${str}">${doc.taskList[0].taskName}</td>
							   <td>${doc.title}</td>
							   <td>${doc.draftName}</td>
							   <td>${doc.docStatName}</td>
							   <td>${date}</td>`));
				copy.find('td').eq(3).addClass("taskTitle");				
				tbody.append(copy);
			}
			
			/*
			$(docs).each(function(idx,doc){
				let tr = tbody.children('tr').eq(0).clone();
				tr.attr('onclick','#');
				tr.append($(`<td>doc.docNo</td>
							 <td></td>
							 <td></td>
							 <td></td>
							 <td></td>
							 <td></td>
							 <td></td>
							 <td></td>`));
				tbody.append(tr);
			})
			*/
			
		},
		errMsg)
	
	
	
}

const searchSVC = {

	schTest(param, success, error) {
		$.ajax({
			url : 'docCmplt/search',
			type : 'post',
			contentType : 'application/json',
			data : JSON.stringify(param),
			beforeSend: function(xhr) {
	        xhr.setRequestHeader(header, token);
	    	}
		})
		.done(success)
		.fail(error)
	}	

}

function errMsg(err){
	console.error('error => ', err);
}

function makeTbody() {

let tbody = $('tbody.resultList').children('*')
			tbody.remove();
			let pages = $('#pageArea').find('ul[class="pagination justify-content-center"]').children('*')
			pages.remove();

copy.append($(`<td>doc.docNo</td>
							   <td></td>
							   <td></td>
							   <td></td>
							   <td></td>
							   <td></td>
							   <td></td>
							   <td></td>`));


}