document.addEventListener("DOMContentLoaded", function() {


    var news = new Vue({
        el: '#news',
        data: {
        	animation:null,
            newses: []
        },
        mounted() {
            axios.get("/news/" + profilename).then(response => {
                response.data.map(ns => {
                    ns.date = dataformat(ns.date);
                    ns.domain = extractDomain(ns.sourceLink);
                    ns.title = clearLocalNews(ns.title);
                    return ns;
                });
                extractDomain(response.data[0].sourceLink);
                this.newses = response.data;
                
            });
        },
        
        updated(){
        	setTimeout(function(){ 
        		if(this.animation)
        			this.animation.cancel();
        			this.animation = animateNewsSection();        		
        	}, 4000);
        }
        
    });


});

function dataformat(date) {
    let fdate = new Date(date);
    fdate = Date.now() - fdate;
    let hoursAgo = Math.floor(fdate / 1000 / 60 / 60);
    let daysAgo = Math.floor(hoursAgo / 24);
    return daysAgo >= 1 ? daysAgo + " dni temu" : hoursAgo + " godzin temu";
}

function extractDomain(url) {
    let regexp = new RegExp("https?:\/\/(www.|)([a-z0-9.-]+.[a-z]{2,5})\/");
    let matches = regexp.exec(url);
    return matches[2];
}

function clearLocalNews(string) {
    let ind = string.search(", powiat lubaczowski, elubaczow.com - aktualne informacje z regionu każdego dnia!");
    return ind != -1 ? string.substring(0, ind) : string;
}
function animateNewsSection(){
	let newsElement = document.getElementById("news");
	let translation = (newsElement.scrollHeight - newsElement.offsetHeight)*-1; 
	let cssTranslation = 'translateY('+translation+'px)';
	newsElement.animate([
		  { transform: 'translateY(0)' },
		  { transform: cssTranslation }  
    	], { 
		  delay:2*1000,
    	  duration: 65*1000,
    	  iterations:Infinity,
    	  direction:'alternate',
    	  easing:'linear',
    	  fill:'forwards',
    	});	   
}

function calcTotalElementHeight(element){
	return Array.from(element.childNodes).map(el=>el.offsetHeight).reduce((a,b)=>a+b);
}
