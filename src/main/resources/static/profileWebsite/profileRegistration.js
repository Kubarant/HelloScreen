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
	}).catch(function (error) {
	     let errors= error.response.data.errors
          let errorMessages =errors.map(err=>err.defaultMessage+"\n").reduce((msg,msg2)=>msg+msg2);
          console.log(errorMessages);
          let errorLabel =document.getElementById("error-label");
           errorLabel.innerText= errorMessages;
        });
	
	
	
	
};



});