function handleFormSubmit(event){
    event.preventDefault();

    let form = $('#newEmployeeForm')[0];
    let formJson = formToJson(form.elements);
    $.ajax({
        url: '/employee',
        type: 'post',
        dataType: 'json',
        contentType: 'application/json',
        success: function (data) {
            $('#target').html(data.msg);
        },
        data: JSON.stringify(formJson)
    });
}

// extracts form data into empty json object
function formToJson(elements){
    return [].reduce.call(elements, (data, element) => {

        data[element.name] = element.value;
        return data;
      
    }, {});
}

function attachSubmitListner(){
    try{
        const form = $("#newEmployeeForm")[0];
        form.addEventListener('submit', handleFormSubmit);    
    }
    catch(e){
        console.log("There was no form to attach");
    }
}