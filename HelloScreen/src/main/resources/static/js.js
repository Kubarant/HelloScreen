document.addEventListener("DOMContentLoaded", function() {
    var weather = new Vue({
        el: '#wth',
        data: {
            wth: []
        },
        mounted() {
            axios.get("/sun").then(response => {
                response.data.temp -= 273.15;
                response.data.temp =Math.floor(response.data.temp);

                let sunset = new Date(response.data.sunset);
                response.data.sunset = sunset.getHours() + ":" + leftpad("0", 2, sunset.getMinutes());

                let sunrise = new Date(response.data.sunrise);
                response.data.sunrise = leftpad("0", 2, sunrise.getHours()) + ":" + leftpad("0", 2, sunrise.getMinutes());

                response.data.weather = "icons/" + response.data.weather + ".png";
                this.wth = response.data;
 
            });
        }
    });



});

function map (num, in_min, in_max, out_min, out_max) {
	  return (num - in_min) * (out_max - out_min) / (in_max - in_min) + out_min;
	}

function leftpad(char, size, characters) {
    let string = characters.toString();
    let addition = "";
    for (let i = string.length; i < size; i++) {
        addition += char;
    }
    return addition + string;
}

function visibilityToLocalString(visibility) {}

function dateformat(datestring) {
    const monthNames = ["Sty", "Lut", "Mar", "Kwi", "Maj", "Cze",
        "Lip", "Sie", "Wrz", "Paż", "Lis", "Gru"
    ];
    let result = "";
    let date = new Date(datestring);
    result = date.getHours() + ":" + leftpad("0", 2, date.getMinutes()) + " " + date.getDate() + " " + monthNames[date.getMonth()];
    return result;

}


function tempColor(value) {
	if(value>0){
		let res = map(value,0,35,120,0)
		res =parseInt(res.toString(16), 16);
		return "#FF"+res+"00AA"
	}
	else
		return 'blue';
}


document.addEventListener("DOMContentLoaded", function() {
    console.log("aasdadsfds");

    axios.get("/forecast").then(response => {
        window.liss = response.data;

        let lista = response.data.list;
        let labels = lista.map(el => dateformat(el.date));
        let temps = lista.map(el => Math.floor(el.temp - 273.15));
        let tempsColors= temps.map(el=>tempColor(el));
        console.log(tempsColors)
        let rain = lista.map(el => el.rain);

        console.log(rain);
        Chart.defaults.global.defaultColor = "#ADADAD88";
        Chart.defaults.global.defaultFontColor = 'ADADAD88';//"#DD2222DD"
        let chart = new Chart(document.getElementById("mixed-chart"), {
            type: "bar",
            data: {
                labels: labels,
                datasets: [{

                    label: "Temperatura",
                    type: "line",
                    borderColor:"#DD2222DD",
                    
                    pointRadius: 3,
                    data: temps,
                    fill: true,

                }, {
                	
                    label: "Opady",
                    type: "line",
                    borderColor: "#1111DD",
                    data: rain,
                    fill: true
                }]
            },
            options: {
                legend: {
                    labels: {
                        fontColor: 'white'
                    }
                },
                scales: {
                    xAxes: [{
                        gridLines: {
                            color: 'rgba(231,231,255,0.6)',

                        }
                    }],
                    yAxes: [{
                        fontColor: 'white',

                        gridLines: {
                            color: 'rgba(231,231,255,0.6)',
                        }
                    }]
                },
                responsive: true

            }
        });
        window.chna = chart;
        for (var i = 0; i < chart.data.datasets.length; i++) {
        	chart.data.datasets[i].borderColor =tempsColors[i];
		}
        
        
    });


});