/**
 * 
 */
var profilename = extractProfilename();
document.addEventListener("DOMContentLoaded", function() {
    let header = new Vue({
        el: "#header",
        data: {
            name: profilename
        }
    })



})

function extractProfilename() {
    let url = window.location.href;
    let name = /http[^?]+\?profilename=(.*)(&|)/.exec(url);
    if (name)
        return name[1] == null ? "unknown" : name[1];
    else
        return "unknown"

}