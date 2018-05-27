/**
 * 
 */
document.addEventListener("DOMContentLoaded", function() {

let submit = document.getElementById("submit");

submit.onclick = function() {
	let name = document.getElementById("name-input").value;
	let sdata =getSlidersData("cat-chooser");
	let a = getKeywords();
	
	axios.post("/register", {
		name:name,
		preferences:sdata,
		keywords:a
	}
	).then(response => {
	    window.location.replace("/ho?profilename="+name);
		console.log(response)
	});
	
	
	
	
};



});