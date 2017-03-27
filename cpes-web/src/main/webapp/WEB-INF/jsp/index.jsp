
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Insert title here</title>
<script type="text/javascript">

	window.onload = function(){
		
		var showTimeEle = document.getElementById("showTime");
		
		showTime();
		
		function showTime() {
			
			showTimeEle.innerHTML = new Date().toLocaleString();
			
			//注意：传入的是函数的引用，不能带()
			setTimeout(showTime, 1000);
		}
		
	};

</script>
</head>
<body>

	<p id="showTime"></p>

</body>
</html>