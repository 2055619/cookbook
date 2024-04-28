import React from 'react'
import {t} from "i18next";
import {IPublication} from "../../assets/models/Publication";
import {IFilters} from "../../assets/models/Form";

interface IFilterObjectList {
    items: any[];
    // attributes: string[];
    renderItem: (items: IPublication[]) => JSX.Element;
    filters: any;
}

function FilterObjectList({items, renderItem, filters}: IFilterObjectList) {

    const filteredItems = Array.isArray(items)
        ? items.filter(item => {
            for (let key in filters) {
                if (filters[key]) {
                    if (Array.isArray(filters[key])) {
                        if (Array.isArray(item[key])) {
                            if (filters[key].length !== 0 && !filters[key].some((dietType: string) => item[key].includes(dietType))) { //!filters[key].some((dietType: string) => item[key].includes(dietType))
                                return false;
                            }

                        } else {
                            if (filters[key].length !== 0 && !filters[key].includes(item[key])) {
                                return false;
                            }
                        }
                    } else if (typeof item[key] === 'string' && typeof filters[key] === 'string' && !String(item[key]).toLowerCase().includes(filters[key].toLowerCase())) {
                        return false;
                    } else if (typeof item[key] === 'number' && Number(filters[key]) !== 0) {
                        if (key === "prepTime" || key === "cookTime") {
                            if (item[key] > Number(filters[key])){
                                return false;
                            }
                        } else if (item[key] <= Number(filters[key])) {
                            return false;
                        }
                    } else if (key === "cookingTime" && Number(filters[key]) !== 0) {
                        if ((item["prepTime"] + item["cookTime"]) > Number(filters[key])){
                            return false;
                        }
                    }
                }
            }
            return true;
        })
        : []


    // const filteredItems = Array.isArray(items)
    //     ? items.filter(item => {
    //         for (let key in filters) {
    //             if (filters[key]) {
    //                 if (Array.isArray(item[key])) {
    //                     // If the item's attribute is an array (like dietType), check if it includes the filter
    //                     if (!item[key].includes(filters[key])) {
    //                         return false;
    //                     }
    //                 } else if (typeof item[key] === 'string' && !String(item[key]).toLowerCase().includes(filters[key].toLowerCase())) {
    //                     return false;
    //                 } else if (typeof item[key] === 'number' && item[key] <= Number(filters[key])) {
    //                     return false;
    //                 }
    //             }
    //
    //             // if (filters[key] && String(item[key])) {
    //             //     return false;
    //             // }
    //
    //
    //             // if (filters[key] && typeof item[key] === 'string' && !String(item[key]).toLowerCase().includes(filters[key].toLowerCase())) {
    //             //     return false;
    //             // }
    //             // if (filters[key] && typeof item[key] === 'number' && item[key] <= Number(filters[key])) {
    //             //     return false;
    //             // }
    //         }
    //         console.log("Filters 2: ", filters)
    //
    //         return true;
    //     })
    //     : []

    // const filteredItems = Array.isArray(items)
    //     ? items.filter(item => {
    //         for (let key in filters) {
    //             if (String(item[key]).toLowerCase() !== filters[key].toLowerCase()) {
    //                 return false;
    //             }
    //         }
    //         return true;
    //     })
    //     : []

    return (
        <div className="">
            <div className="">
                {renderItem(filteredItems)}
            </div>
        </div>
    )
}

FilterObjectList.defaultProps = {
    items: [],
    attributes: [],
    renderItem: () => {
        return (<>{t('noData')}</>)
    },
    filters: {}
}

export default FilterObjectList
