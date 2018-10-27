
document.addEventListener("DOMContentLoaded", function() {
let submit = document.getElementById("submit");

submit.onclick = function() {
    disableSubmitButton();
	let name = document.getElementById("name-input").value;
	let sdata =getSlidersData("cat-chooser");
	let keywords = getKeywords();
	
	axios.post("/register", {
		name:name,
		preferences:sdata,
		keywords:keywords
	})
	.then(response => {
	    window.location.replace("/home?profilename="+name);
		console.log(response)
	})
	.catch(function (error) {
	    enableSubmitButton();
	    showErrorMessage(error)
	     });
};
});

function showErrorMessage(error){
    let errors= error.response.data.errors
    let errorMessages =errors.map(err=>err.defaultMessage+"\n").reduce((msg,msg2)=>msg+msg2);
    console.log(errorMessages);
    let errorLabel =document.getElementById("error-label");
    errorLabel.innerText= errorMessages;
}

function  disableSubmitButton(){
    let submit =document.getElementById("submit");
    submit.disabled = true;
    submit.style.color="grey";
}

function  enableSubmitButton(){
    let submit =document.getElementById("submit");
    submit.disabled = true;
    submit.style.color="#f34263";
}