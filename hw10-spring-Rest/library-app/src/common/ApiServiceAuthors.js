const apiUrlAuthors = '/library/api/authors/';

export function loadAllAuthors() {
    return fetch(apiUrlAuthors)
        .then(res => res.json())
        .then((data) => {
            return data;
        })
        .catch(console.log)
}

export function createOrUpdateAuthor(create) {
    const requestOptions = {
        method: create ? 'POST' : 'PUT',
        headers: {'Content-Type': 'application/json'},
        body: JSON.stringify(this.state.author)
    };
    fetch(apiUrlAuthors, requestOptions)
        .then(response => response.json())
        .then(data => this.setState({author: data}))
        .then(() => alert(create ? 'Author created!' : 'Author updated!'));
}

export function deleteAuthor(author) {
    return fetch(apiUrlAuthors + author.id, {
        method: 'DELETE',
        headers: {'Content-Type': 'application/json'}
    }).then(response => {
            if (response.ok) {
                alert("Author deleted!");
            } else {
                response.text().then(text => {
                    alert(text);
                })
            }
        }
    ).catch(reason =>
        console.log(reason)
    )
}