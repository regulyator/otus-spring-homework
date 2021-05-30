const apiUrlGenres = '/library/api/genres/';

export function loadAllGenres() {
    return fetch(apiUrlGenres)
        .then(res => res.json())
        .then((data) => {
            return data;
        })
        .catch(console.log)
}

export function createOrUpdateGenre(create) {
    const requestOptions = {
        method: create ? 'POST' : 'PUT',
        headers: {'Content-Type': 'application/json'},
        body: JSON.stringify(this.state.genre)
    };

    fetch(apiUrlGenres, requestOptions)
        .then(response => response.json())
        .then(data => this.setState({genre: data}))
        .then(() => alert(create ? 'Genre created!' : 'Genre updated!'));
}

export function deleteGenre(genre) {
    return fetch(apiUrlGenres + genre.id, {
        method: 'DELETE',
        headers: {'Content-Type': 'application/json'}
    }).then(response => {
            if (response.ok) {
                alert("Genre deleted!");
            } else {
                response.text().then(text => {
                    alert(text)
                })
            }
        }
    ).catch(reason => console.log(reason))
}