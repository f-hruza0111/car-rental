import Card from './Card'

function AdGallery({ads})  {

    // console.log(ads)

    return (
        <div className="d-inline-flex align-items-center flex-wrap justify-content-around">
            {ads.map(ad =>(    
                    <Card key={ad.id} ad ={ad}/>   
                  
            ))}
        </div>
    )
}

export default AdGallery;