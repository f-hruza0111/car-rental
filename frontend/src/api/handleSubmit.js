import axios from "../api/axios";
import serializeFormToJSON from "./serializeFormToJSON";

async function handleSubmit(e, url){
    e.preventDefault()
   
  
    const jsonData = serializeFormToJSON(e)
 
    try {
      const res = await axios.post(url, jsonData, {
        headers: {
          'Content-Type': 'application/json'
        }
      });
    

      return res;
    } catch(err){
      console.log(`Error while submiting form to ${url}. Error: ${err}`)
    }
    

}

export default handleSubmit