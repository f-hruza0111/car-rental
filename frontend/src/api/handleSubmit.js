import axios from "../api/axios";
import serializeFormToJSON from "./serializeFormToJSON";

async function handleSubmit(e, url){
    e.preventDefault()
   
  
    const jsonData = serializeFormToJSON(e)
 
    const res = await axios.post(url, jsonData, {
        headers: {
          'Content-Type': 'application/json'
        }
      });
    

    return res;

}

export default handleSubmit