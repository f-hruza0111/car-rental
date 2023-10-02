import serializeFormToJSON from "./serializeFormToJSON";
import sendRequest from "./protectedHttpRequsts";



async function handleUpdate(e, url){
    e.preventDefault()
   
  
    const jsonData = serializeFormToJSON(e)
    console.log(jsonData)
    

    const res = await sendRequest("post", url, jsonData)

    // console.log(res)
}


export default handleUpdate