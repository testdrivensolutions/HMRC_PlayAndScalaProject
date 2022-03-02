$( document ).ready(function() {
    $( ".test" ).click(function() {
        alert( "Correct Answer!" );
    });

    //get all elements
    var all = $(".card").map(function() {
        return this;
    }).get();

    //set min and max index
    currentIndex = 0;
    maxIndex = all.length -1;

    //handle next question button click event
    $("#next").click(function() {
        currentIndex = incrementQuestionIndex();
    });

    //increment or reset index, use bootstrap to remove/add classes to control style, scroll to current question
    function incrementQuestionIndex() {
        if(currentIndex < maxIndex){
            removeHighlight(all[currentIndex]);
            ++currentIndex;
            all[currentIndex].scrollIntoView();
            addHighlight(all[currentIndex]);
            return currentIndex;
        }else{
            removeHighlight(all[currentIndex]);
            currentIndex=0;
            addHighlight(all[currentIndex]);
            all[currentIndex].scrollIntoView();
            return currentIndex;
        }
    }

    function addHighlight(element){
        $(element).removeClass( "text-white bg-dark" )
    }

    function removeHighlight(element){
        $(element).addClass( "text-white bg-dark" )
    }
});