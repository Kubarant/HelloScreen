document.addEventListener("DOMContentLoaded", function() {

    let sales = new Vue({
        el: '#sales',
        data: {
            products: []
        },
        mounted() {
            axios.get("/prod/" + profilename).then(response => {
              response.data.map(el => el.url = "/imgosy/" + el.imagePath + ".jpg");
                response.data.map(el => {
                    let price = el.price.split(".");
                    el.pln = price[0];
                    el.cents = price[1];
                    return el;
                });
                this.products = response.data;


            });
        }
    });



});

function validate(name) {
    let res = name.toLowerCase();
    res = res.replace(new RegExp('[^a-z0-9]', 'g'), "");

}