import React from 'react'
import {t} from "i18next";
import {IPublication} from "../../assets/models/Publication";

interface IFilterObjectList {
    items: any[];
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
