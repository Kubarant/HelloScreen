document.addEventListener("DOMContentLoaded", function() {


    var news = new Vue({
        el: '#news',
        data: {
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