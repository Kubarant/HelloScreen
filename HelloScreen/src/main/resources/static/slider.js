document.addEventListener("DOMContentLoaded", function() {
    let slidersHC = document.getElementsByClassName("slider");
    var sliders = Array.from(slidersHC);
    sliders.forEach(slider => slider.value = 25);
    sliders.forEach(el => updateLabels(el));
    sliders.forEach(slider => slider.onchange = function() {


    });

    sliders.forEach(slider => slider.oninput = function() {
        let sliders = Array.from(document.getElementsByClassName("slider"));
        let sum = 0;
        //        sliders.forEach(el=>console.log(el.value));
        sliders.forEach(el => sum += Number.parseInt(el.value));
        //console.log(sum+" ehh");
        if (sum > 100) {
            let other = sliders.filter(el => el != this && el.value > 0);
            let randomOther = other[Math.floor(Math.random() * other.length)];
            randomOther.value -= sum - 100;
        } else if (sum <= 100 && sum >= 0) {
            let othersss = sliders.filter(el => el != this && el.value >= 0);
            let others = getLowerSliders(this);

            let randomOthers;
            if (others != null && others.length > 0) {
                randomOthers = others[Math.floor(Math.random() * others.length)];
            } else {
                randomOthers = getHigherSliders(this)[0][0];
            }

            let difference = Number.parseInt(randomOthers.value) + (100 - sum);

            //           console.log(difference);
            randomOthers.value = difference;

        }
        sliders.forEach(el => updateLabels(el));


    });
});


function updateLabels(slider) {

    let childs = getAllElementsFromSameLevel(slider);
    //	console.log(childs);
    let labels = findElementsContainingClass(childs, "slider-label");
    //	console.log(labels);
    labels.forEach(el => el.value = slider.value);
}


function getAllElementsFromSameLevel(element) {
    return Array.from(element.parentNode.childNodes);
}

function getAllElementsFromSameLevelWithoutArg(element) {
    return getAllElementsFromSameLevel(element).filter(el => el != element);
}

function getLowerSliders(currentSlider) {
    let parent = currentSlider.parentNode;
    let otherParents = getAllElementsFromSameLevel(parent);
    otherParents = findElementsContainingClass(otherParents, "slider-group");
    for (var i = 0; i < otherParents.length; i++) {
        if (otherParents[i] == parent) {
            let maximumIndex = otherParents.length;
            let endindex = i == maximumIndex ? maximumIndex : i + 1;
            return otherParents.map(el => findElementsContainingClass(Array.from(el.childNodes), "slider"))[endindex];
        }
    }
    return [];
}

function getHigherSliders(currentSlider) {
    let parent = currentSlider.parentNode;
    let otherParents = getAllElementsFromSameLevel(parent);
    otherParents = findElementsContainingClass(otherParents, "slider-group");
    for (var i = otherParents.length; i > 0; i--) {
        if (otherParents[i] == parent) {
            let maximumIndex = otherParents.length;
            let endindex = i == maximumIndex ? maximumIndex : i + 1;
            return otherParents.map(el => findElementsContainingClass(Array.from(el.childNodes), "slider")).slice(0, i);
        }
    }
    return [];
}

function findElementsContainingClass(elements, CSSclass) {
    return elements.filter(el => el.classList).filter(el => el.classList.contains(CSSclass));
}


function getSlidersData(slidersid) {
    let sliders = document.getElementById(slidersid);
    let slidersGroup = findElementsContainingClass(Array.from(sliders.childNodes), "slider-group");
    let svalues = slidersGroup.map(sgroup => extractSliderData(sgroup));
    return svalues;

}

function extractSliderData(slidersGroup) {
    let childs = Array.from(slidersGroup.childNodes);
    let label = findElementsContainingClass(childs, "slider-category")[0].innerText;
    let value = findElementsContainingClass(childs, "slider")[0].value;
    let sliderData = {
        label: label,
        value: value
    };
    return sliderData;
}