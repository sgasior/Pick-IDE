// -- - TOGGLE CHECKBOX START-- -

if ($("#toggle-checkbox").is(':checked')) {
    $("label[for='toggle-checkbox']").before().addClass("active-toggle");
}

$('#toggle-checkbox:checkbox').click(function () {
    $("label[for='toggle-checkbox']").before().toggleClass("active-toggle");
});

// -- - TOGGLE CHECKBOX END-- -


// -- - MULTIPLE STEP FORM START-- -
var currentTab = 0;
displayTab(currentTab);


function updateStepActive(n) {

    step = $(".step-circle");
    for (var i = 0; i < step.length; i++) {
        step[i].className = step[i].className.replace(" active-step", "");
    }

    step[n].className += " active-step";
}


function displayTab(tabNumber) {
    var tab = $(".tab");
    tab[tabNumber].style.display = "block";

    if (currentTab == 0) {
        $("#prevBtn").css("display", "none");
        $(".stepsButtons #addCriteria").css("display", "none");
    } else {
        $("#prevBtn").css("display", "inline-block");
    }

    if (currentTab == 1) {
        $(".stepsButtons #addCriteria").css("display", "inline-block");
        $("#nextBtn").css("margin-left", "0");
    } else {
        $("#nextBtn").css("margin-left", "auto");
    }

    if (currentTab == 2) {
        $(".stepsButtons #addCriteria").css("display", "none");
        $("#addAlternative").css("display", "inline-block");
        $("#nextBtn").css("margin-left", "0");
    } else {
        $("#addAlternative").css("display", "none");
    }


    if (currentTab == (tab.length - 1)) {
        document.getElementById("nextBtn").innerHTML = "Submit";
    } else {
        document.getElementById("nextBtn").innerHTML = "Next";
    }

    updateStepActive(tabNumber);
}


function nextView(n) {
    var tab = $(".tab");
    console.log(currentTab);
    tab[currentTab].style.display = "none";
    currentTab = currentTab + n;
    if (currentTab >= tab.length) {
        document.getElementById("surveyForm").submit();
        return false;
    }
    displayTab(currentTab);
}

// -- - MULTIPLE STEP FORM END-- -


// -- - DYNAMICALLY ADDING FORMS START-- -


var criteria_id_counter = 1;
var totalCriteriasCreated = 1;

var alternative_id_counter = 1;
var totalAlternativesCreated = 1;
var max = 5;


function addCriterias() {
    if (totalCriteriasCreated == max) {
        alert("Sorry but you have reached the limit of adding criterias");
        return;
    }
    $("#tab2").css("display", "none");
    $(".stepsButtons").css("display", "none");
    $("#tab-add-criteria").css("display", "block");
}


function acceptCriterias(divName, criteriaName) {
    if (criteriaName.length === 0) {
        alert("Criteria name cannot be empty");
        return;
    }

    $(".new-inputs-msg").css("display", "block");
    var newdiv = document.createElement("div");
    newdiv.innerHTML = "<div class='checkbox' id='checkbox_criteria_" + criteria_id_counter + "'><button type='button' class='deleteCheckbox' id='deleteCriteria_" + criteria_id_counter + "' onclick='deleteCriteria(this.id)'>X</button><input type='checkbox' id='newCriteria_" + criteria_id_counter + "'name='selectedCriteria' value='" + criteriaName + "'> <label for = 'newCriteria_" + criteria_id_counter + "'>" + criteriaName + "</label> </div>";
    criteria_id_counter++;
    totalCriteriasCreated++;
    document.getElementById(divName).appendChild(newdiv);

    $("#tab2").css("display", "block");
    $(".stepsButtons").css("display", "flex");
    $("#tab-add-criteria").css("display", "none");

}


function cancelAddingCriteria() {
    $("#tab2").css("display", "block");
    $(".stepsButtons").css("display", "flex");
    $("#tab-add-criteria").css("display", "none");
}

function addAlternatives() {
    if (totalAlternativesCreated == max) {
        alert("Sorry but you have reached the limit of adding alternatives");
        return;
    }
    $("#tab3").css("display", "none");
    $(".stepsButtons").css("display", "none");
    $("#tab-add-alternatives").css("display", "block");
}

function acceptAlternatives(divName, alternativeName) {

    if (alternativeName.length === 0) {
        alert("Alternative name cannot be empty");
        return;
    }

    $(".new-inputs-msg").css("display", "block");

    var newdiv = document.createElement("div");

    newdiv.innerHTML = "<div class='checkbox' id='checkbox_alternative_" + alternative_id_counter + "'><button type='button' class='deleteCheckbox' id='deleteAlternative_" + alternative_id_counter + "' onclick='deleteAlternative(this.id)'>X</button><input type='checkbox' id='newAlternative_" + alternative_id_counter + "' name='selectedAlternative' value='" + alternativeName + "'> <label for = 'newAlternative_" + alternative_id_counter + "'>" + alternativeName + "</label> </div>";

    alternative_id_counter++;
    totalAlternativesCreated++;
    document.getElementById(divName).appendChild(newdiv);

    $("#tab3").css("display", "block");
    $(".stepsButtons").css("display", "flex");
    $("#tab-add-alternatives").css("display", "none");

}


function cancelAddingAlternatives() {
    $("#tab3").css("display", "block");
    $(".stepsButtons").css("display", "flex");
    $("#tab-add-alternatives").css("display", "none");
}

function deleteCriteria(clicked_id) {
    id = clicked_id.substring(clicked_id.indexOf("_") + 1, clicked_id.length);

    var el = document.getElementById('checkbox_criteria_' + id);
    el.remove();
    totalCriteriasCreated--;
}

function deleteAlternative(clicked_id) {
    id = clicked_id.substring(clicked_id.indexOf("_") + 1, clicked_id.length);

    var el = document.getElementById('checkbox_alternative_' + id);
    el.remove();
    totalAlternativesCreated--;
}
