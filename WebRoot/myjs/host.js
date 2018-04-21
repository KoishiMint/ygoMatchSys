function create_match(){
	var macName = $("#match-name").val();
	var macInfo = $("#match-info").val();
	var macTime = $("#ECalendar_date").val();
	var macType = "";
	var temp = document.getElementsByName("macth-type");
	for(var i=0;i<temp.length;i++){
		if(temp[i].checked){
			macType = temp[i].value;
		}
	}
	var regNum = $('select#match-max-num option:selected').val();
	var macBan = $('select#match-ban-list option:selected').val();
	console.log("macName=" + macName + " macInfo=" + macInfo + " macTime=" + macTime + " macType=" + macType + " regNum=" + regNum + " macBan=" + macBan);
	host.createMac(macName,macTime,macInfo,macType,regNum,macBan,callbackCreateMatch);
	console.log("createMac success");
}

function callbackCreateMatch(CreateMatchResult){
	if(CreateMatchResult == 0){
		console.log("success create match");
	} else {
		console.log("fail create match");
	}
}