import {cookServerInstance} from "../App";

export class UtilsService {
    async getValidationPattern() {
        return cookServerInstance.get('/utils/validation-patterns')
            .then((response) => response.data);
    }

    async getDifficultyLevels() {
        return cookServerInstance.get('/utils/difficulty-levels')
            .then((response) => response.data);
    }

    async getCategories() {
        return cookServerInstance.get('/utils/categories')
            .then((response) => response.data);
    }

    async getIngrediantStates() {
        return cookServerInstance.get('/utils/ingredient-states')
            .then((response) => response.data);
    }

    async getVisibility() {
        return cookServerInstance.get('/utils/visibility')
            .then((response) => response.data);
    }

    async getPortionSizes() {
        return cookServerInstance.get('/utils/portion-sizes')
            .then((response) => response.data);
    }

    async getDietTypes() {
        return cookServerInstance.get('/utils/diet-types')
            .then((response) => response.data);
    }

    async getUnits() {
        return cookServerInstance.get('/utils/units')
            .then((response) => response.data);

    }
}