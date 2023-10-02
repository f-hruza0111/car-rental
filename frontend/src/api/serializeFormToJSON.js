function serializeFormToJSON(e){
    var data1 = new FormData(e.target);
    
    let formObject = Object.fromEntries(data1.entries());
   
    const jsonData = JSON.stringify(formObject)

    return jsonData;
}

export default serializeFormToJSON;
   
    