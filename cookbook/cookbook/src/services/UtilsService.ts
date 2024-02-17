import {cookServerInstance} from "../App";

export class UtilsService {
    async getValidationPattern() {
        return cookServerInstance.get('/utils/validation-patterns').then((response) => response);
    }

    async getDifficultyLevels() {
        return cookServerInstance.get('/utils/difficulty-levels').then((response) => response);
    }

    async getCategories() {
        return cookServerInstance.get('/utils/categories').then((response) => response);
    }

    async getIngrediantStates() {
        return cookServerInstance.get('/utils/ingredient-states').then((response) => response);
    }

    async getVisibility() {
        return cookServerInstance.get('/utils/visibility').then((response) => response);
    }
}