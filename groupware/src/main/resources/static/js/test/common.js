function today() {

var today = new Date();
			
			var year = today.getFullYear();
			var month = ('0' + (today.getMonth() + 1)).slice(-2);
			var day = ('0' + today.getDate()).slice(-2);
			
			var date = year + '-' + month + '-' + day; // date = 현재 날짜
			
			var hours = ('0' + today.getHours()).slice(-2);
			var minutes =('0' + today.getMinutes()).slice(-2);
			var seconds = ('0'+ today.getSeconds()).slice(-2);
			
			var time = hours + ':' + minutes + ':' + seconds; // time = 현재 시간
		return {date, time};
};