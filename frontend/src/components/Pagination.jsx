import React from "react";

function Pagination({adsPerPage, totalPages, paginate}) {
    const pageNumbers = [];

    // console.log("pagination...")
    // console.log(totalPages)

    for(let i = 1; i <= totalPages; i++){
        // console.log("pushing page")
        pageNumbers.push(i);
    }

    // console.log(totalPages)
    // console.log(pageNumbers)

    return (
        <nav>
            <ul className="pagination">
                {pageNumbers.map(number => (
                    <li key={number} className="page-item">
                        <a onClick={() => paginate(number - 1)} className="page-link">
                            {number}
                        </a>
                    </li>
                ))}
            </ul>
        </nav>
    )
}

export default Pagination;