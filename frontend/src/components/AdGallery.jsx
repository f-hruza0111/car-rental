import Card from './Card'

function AdGallery({ads})  {

    // console.log(ads)

    let response = <h1>No ads found for this company</h1>
    // console.log(ads.length)
    // console.log(ads)


    if(ads.length !== 0){
        response =  <div className="d-inline-flex align-items-center flex-wrap justify-content-around">
                                    {ads.map(ad =>(    
                                            <Card key={ad.id} ad ={ad}/>   
                                        
                                    ))}
                                </div>
    } 

    return response
}

export default AdGallery;