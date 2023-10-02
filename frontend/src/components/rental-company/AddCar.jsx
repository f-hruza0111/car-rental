import sendRequest from "../../api/protectedHttpRequsts";
import { useParams } from "react-router-dom";
import Navbar from "../Navbar";
import handleUpdate from "../../api/handleUpdate";
function AddCar(){
    let id = sessionStorage.getItem('user_id')
    let url = `/rental-company/${id}/add-car`

    return (  
  
        <>
               <Navbar/>
                <div className='container'>   
                    <form onSubmit={e => handleUpdate(e, url)}>
                        <div class="form-group">
                            <label for="make">Make</label>
                            <input type="text" class="form-control" id="make" name='make'/>

                            <label for="model">Model</label>
                            <input type="text" class="form-control" id="model" name='model'/>

                            <label for="licensePlate">License plate</label>
                            <input type="text" class="form-control" id="licensePlate" name='licensePlate'/>

                            <label for="yearOfProduction">Year of production</label>
                            <input type="number" min={1929} class="form-control" id="yearOfProduction" name='yearOfProduction'/>

                            <label for="horsepower">Horsepower</label>
                            <input type="number" min={1} class="form-control" id="horsepower" name='horsepower'/>

                            <label for="kilometers">Kilometers</label>
                            <input type="number" min={0} class="form-control" id="kilometers" name='kilometers'/>

                            <label for="registeredUntil">Registered until</label>
                            <input type="date" min={0} class="form-control" id="registeredUntil" name='registeredUntil'/>

                            <label for="engineType">Engine type</label>
                            <select name="engineType" class="form-select" >
                                <option value="DIESEL">DIESEL</option>
                                <option value="ELECTRIC">ELECTRIC</option>
                                <option value="PETROL">PETROL</option>
                                <option value="HYBRID">HYBRID</option>
                            </select>

                            <label for="transmissionType">Transmission type</label>
                            <select name="transmissionType" class="form-select">
                                <option value="AUTOMATIC">AUTOMATIC</option>
                                <option value="MANUAL">MANUAL</option>
                            </select>

                            <label for="powerDelivery">Power delivery</label>
                            <select name="powerDelivery" class="form-select">
                                <option value="FWD">FWD</option>
                                <option value="RWD">RWD</option>
                                <option value="AWD">AWD</option>
                            </select>

                            
    
                           
                            <button className="btn btn-primary" type="submit">Add car</button>
                        </div>
                    </form> 
                </div>
        </>
      )
}

export default AddCar