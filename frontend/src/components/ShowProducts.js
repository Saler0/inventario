import React, {useEffect, useState} from "react";
import axios from "axios";
import Swal from "sweetalert2";
import withReactContent from "sweetalert2-react-content";
import { show_alert } from "../functions";

const ShowProducts = () => {
    const url = "http://localhost:4000/producto";
    const [products, setProducts] = useState([]);
    const [id_PROD, setId_PROD] = useState("");
    const [nombre, setNombre] = useState("");
    const [descripcion, setDescripcion] = useState("");
    const [stock, setStock] = useState("");
    const [operation, setOperation] = useState(1);
    const [title, setTitle] = useState("");

    useEffect(() => { getProducts(); }, []);

    const getProducts = async () => {
        const response = await axios.get(url)
        setProducts(response.data);
    }

    const openModal = (operation, id_PROD, nombre, descripcion, stock) => {
        setId_PROD('');
        setNombre('');
        setDescripcion('');
        setStock('');
        setOperation(operation);

        if (operation === 1) {
            setTitle('Nuevo Producto');
        } else {
            setTitle('Editar Producto');
            setId_PROD(id_PROD);
            setNombre(nombre);
            setDescripcion(descripcion);
            setStock(stock);
        }

        window.setTimeout(function() {
            document.getElementById('nombre').focus();
        }, 500);
    }

    const validateData = () => {
        var parameters;
        var method;

        if (nombre.trim() === "") {
            show_alert("Ingrese el nombre del producto", "warning");
        } else if (descripcion.trim() === "") {
            show_alert("Ingrese la descripción del producto", "warning");
        } else if (stock === "") {
            show_alert("Ingrese el stock del producto", "warning");
        } else {
            if (operation === 1) {
                parameters = {nombre:nombre.trim(), descripcion:descripcion.trim(), stock: stock};
                method = "POST";
            } else if (operation === 2) {
                parameters = {id_PROD:id_PROD, nombre:nombre.trim(), descripcion:descripcion.trim(), stock: stock};
                method = "PUT";
            }

            submitRequest(method, parameters);
        }
    }

    const deleteProduct = (id_PROD, nombre) => {
        const MySwal = withReactContent(Swal);
        MySwal.fire({
            title: "¿Está seguro de borrar el producto " + nombre + "?",
            icon: "question",
            text: "Esta operación no se podrá deshacer",
            showCancelButton: true,
            confirmButtonText: "Borrar",
            cancelButtonText: "Cancelar"
        }).then((result) => {
            if (result.isConfirmed) {
                setId_PROD(id_PROD);
                submitRequest("DELETE", {id_PROD: id_PROD});
            }
        });
    }

    const submitRequest = async (method, parameters) => {
        let endpoint;
        let actionPerformed;

        switch(method) {
            case "POST":
                endpoint = url;
                actionPerformed = "creado"
                break;
            case "PUT":
                endpoint = url + "/" + parameters.id_PROD;
                actionPerformed = "editado"
                break;
            case "DELETE":
                endpoint = url + "/" + parameters.id_PROD;
                actionPerformed = "borrado"
                break;
            default:
                console.log("Invalid method");
                break;
        }

        await axios({method: method, url: endpoint, data: parameters})
            .then(function(response) {
                show_alert("Producto " + actionPerformed + " exitosamente", "success");
                document.getElementById("btn-close-modal").click();
                getProducts();
            }).catch(function(error) {
                show_alert("Error en la solicitud", "error");
                console.log(error);
            });
    }

    return (
        <div className="App">
            <div className="container-fluid">
                <div className="row mt-3">
                    <div className="col-md-4 offset-md-4">
                        <div className="d-grid mx-auto">
                            <button id="btn-open-modal" onClick={() => openModal(1)} className="btn btn-dark" data-bs-toggle="modal" data-bs-target="#modalProducts">
                                <i className="fa-solid fa-circle-plus"></i> add
                            </button>
                        </div>
                    </div>
                </div>
                <div className="row mt-3">
                    <div className="col-12 col-lg-8 offset-0 offset-lg-2">
                        <div className="table-responsive">
                            <table className="table table-bordered">
                                <thead>
                                    <tr><th>ID</th><th>PRODUCTO</th><th>DESCRIPCION</th><th>STOCK</th><th></th></tr>
                                </thead>
                                <tbody className="table-group-divider">
                                    {products.map(product => (
                                        <tr key={product.id_PROD}>
                                            <td>{product.id_PROD}</td>
                                            <td>{product.nombre}</td>
                                            <td>{product.descripcion}</td>
                                            <td>{product.stock}</td>
                                            <td>
                                                <button id="btn-edit-grid" onClick={() => openModal(2, product.id_PROD, product.nombre, product.descripcion, product.stock)} 
                                                    className="btn btn-warning" data-bs-toggle="modal" data-bs-target="#modalProducts">
                                                    <i className="fa-solid fa-edit"></i>
                                                </button>
                                                &nbsp;
                                                <button id="btn-delete-grid" onClick={() => deleteProduct(product.id_PROD, product.nombre)} className="btn btn-danger">
                                                    <i className="fa-solid fa-trash"></i>
                                                </button>
                                            </td>
                                        </tr>
                                    ))
                                    }
                                </tbody>
                            </table>  
                        </div>
                    </div>
                </div>
            </div>
            <div id="modalProducts" className="modal fade" aria-hidden="true">
                <div className="modal-dialog">
                    <div className="modal-content">
                        <div className="modal-header">
                            <label className="h5">{title}</label>
                            <button type="button" className="btn-close" data-bs-dismiss="modal" aria-label="Close"></button>
                        </div>
                        <div className="modal-body">
                            <input type="hidden" id='id_PROD'></input>
                            <div className="input-group mb-3">
                                <span className="input-group-text"><i className="fa-solid fa-gift"></i></span>
                                <input type="text" id="nombre" className="form-control" placeholder="Nombre" value={nombre}
                                onChange={(e) => setNombre(e.target.value)}></input>
                            </div>
                            <div className="input-group mb-3">
                                <span className="input-group-text"><i className="fa-solid fa-comment"></i></span>
                                <input type="text" id="descripcion" className="form-control" placeholder="Descripcion" value={descripcion}
                                onChange={(e) => setDescripcion(e.target.value)}></input>
                            </div>
                            <div className="input-group mb-3">
                                <span className="input-group-text"><i className="fa-solid fa-warehouse"></i></span>
                                <input type="text" id="stock" className="form-control" placeholder="Stock" value={stock}
                                onChange={(e) => setStock(e.target.value)}></input>
                            </div>
                            <div className="d-grid col-6 mx-auto">
                                <button id="btn-save-modal" onClick={() => validateData()} className="btn btn-success">
                                    <i className="fa-solid fa-floppy-disk"></i> Guardar
                                </button>
                            </div>
                        </div>
                        <div className="modal-footer">
                            <button type="button" id="btn-close-modal" className="btn btn-secondary" data-bs-dismiss="modal">Cerrar</button>
                        </div>
                    </div>
                </div>
            </div>
        </div>
    );
};

export default ShowProducts