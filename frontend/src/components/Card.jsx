
import { Link } from "react-router-dom";
import axios from "../api/axios";
import sendRequest from "../api/protectedHttpRequsts";
import serializeFormToJSON from "../api/serializeFormToJSON";

function Card({ad}){
    // console.log(ad)

    var user_id = sessionStorage.getItem("user_id");
    var role = sessionStorage.getItem('role')
    var rentURL =   "/customer/" + user_id + "/" + ad.id + "/rent"
    // console.log(rentURL)

   async function rentACar(e){
        e.preventDefault()
        let res = null;

        if(user_id !== undefined && user_id !== null){
            if(role !== 'CUSTOMER') {window.location.href ='/'}

            const data = serializeFormToJSON(e)
            res = await sendRequest('post', rentURL, data)
        } else {
            window.location.href = '/login'
        }
        // todo: implent error handling

        console.log(res)
   }         

    

   return(

    <div className="card  text-bg-dark p-3" style={{width: 20 + 'rem', margin: 2 + 'rem'}}>

        
            <div className="card-body">
                <h5 className="card-title">{ad.car.make} {ad.car.model} <button className="btn btn-primary">Details</button></h5>
                <h6 className="card-subtitle">Year of production: {ad.car.yearOfProduction}</h6>
                
                <Link className="card-link" to = 
                    {{
                        pathname: "/company/details",
                        search: `?id=${ad.rentalCompany.id}`
                    }}>
                    Rental Company: {ad.rentalCompany.name}
                </Link>
                
                
                
            </div>
        
        
        <div className="card-body">
            <form onSubmit={(e) => rentACar(e)}>
                <label htmlFor="daysRentedFor">Number of days you want to rent for:</label>
                <input type="number" name="daysRentedFor" min={1}></input>  

                <button type="submit" className="btn btn-primary">
                    {"Rent for " + ad.dailyRentCost + "€ a day"}
                </button>
            </form>
        
        </div>
    </div>


   ) 
}

export default Card;