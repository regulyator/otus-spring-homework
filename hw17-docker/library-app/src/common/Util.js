export function handleErrors(response) {
    if (!response.ok) {
        if (response.status === 403) {
            throw Error("You don't have permission!");
        }
    }
    return response

}