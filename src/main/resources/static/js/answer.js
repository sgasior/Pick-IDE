function setSaatyScale(inputID) {
    var value = document.getElementById(inputID).value;
    var saatyScale;


    if (value == 9) {
        saatyScale = "Equal";
    } else if (value == 8 || value == 10) {
        saatyScale = "Equally to moderately";
    } else if (value == 7 || value == 11) {
        saatyScale = "Moderate";
    } else if (value == 6 || value == 12) {
        saatyScale = "Modaretly to strong";
    } else if (value == 5 || value == 13) {
        saatyScale = "Strong";
    } else if (value == 4 || value == 14) {
        saatyScale = "Strongly to very strong";
    } else if (value == 3 || value == 15) {
        saatyScale = "Very strong";
    } else if (value == 2 || value == 16) {
        saatyScale = "Very strong to extremely";
    } else if (value == 1 || value == 17) {
        saatyScale = "Extreme";
    }

    $("." + inputID).html(saatyScale);
}
