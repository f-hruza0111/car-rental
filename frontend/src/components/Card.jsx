
import axios from "../api/axios";
import sendRequest from "../api/protectedHttpRequsts";
import serializeFormToJSON from "../api/serializeFormToJSON";

function Card({ad}){
    // console.log(ad)

    var user_id = sessionStorage.getItem("user_id");
    var rentURL =   "/customer/" + user_id + "/" + ad.id + "/rent"
    // console.log(rentURL)

   async function rentACar(e){
        e.preventDefault()
        let res = null;

        if(user_id !== undefined && user_id !== null){
            const data = serializeFormToJSON(e)
            res = await sendRequest('post', rentURL, data)
        } else {
            window.location.href = '/login'
        }
        // todo: implent error handling

        console.log(res)
   }         

    

   return(

    <div className="card" style={{width: 20 + 'rem', margin: 2 + 'rem'}}>

        
            <div className="card-body">
                <h5 className="card-title">{ad.car.make} {ad.car.model}</h5>
                <h6 className="card-subtitle">{ad.car.yearOfProduction}</h6>
                
            
                <a href={/*change to Link*/"http://localhost:8080/car-rental/company-profile/" + ad.rentalCompany.id}  className="card-link">Rental Company: {ad.rentalCompany.name}</a>
                
                
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