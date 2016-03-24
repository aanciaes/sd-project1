
package sd.clt.ws;

import javax.xml.bind.JAXBElement;
import javax.xml.bind.annotation.XmlElementDecl;
import javax.xml.bind.annotation.XmlRegistry;
import javax.xml.namespace.QName;


/**
 * This object contains factory methods for each 
 * Java content interface and Java element interface 
 * generated in the sd.clt.ws package. 
 * <p>An ObjectFactory allows you to programatically 
 * construct new instances of the Java representation 
 * for XML content. The Java representation of XML 
 * content can consist of schema derived interfaces 
 * and classes representing the binding of schema 
 * type definitions, element declarations and model 
 * groups.  Factory methods for each of these are 
 * provided in this class.
 * 
 */
@XmlRegistry
public class ObjectFactory {

    private final static QName _ListPicturesResponse_QNAME = new QName("http://srv.sd/", "listPicturesResponse");
    private final static QName _CreateAlbumResponse_QNAME = new QName("http://srv.sd/", "createAlbumResponse");
    private final static QName _GetPictureDataResponse_QNAME = new QName("http://srv.sd/", "getPictureDataResponse");
    private final static QName _CreateAlbum_QNAME = new QName("http://srv.sd/", "createAlbum");
    private final static QName _ListPictures_QNAME = new QName("http://srv.sd/", "listPictures");
    private final static QName _DeletePictureResponse_QNAME = new QName("http://srv.sd/", "deletePictureResponse");
    private final static QName _GetPictureData_QNAME = new QName("http://srv.sd/", "getPictureData");
    private final static QName _IOException_QNAME = new QName("http://srv.sd/", "IOException");
    private final static QName _ListAlbums_QNAME = new QName("http://srv.sd/", "listAlbums");
    private final static QName _FileNotFoundException_QNAME = new QName("http://srv.sd/", "FileNotFoundException");
    private final static QName _DeletePicture_QNAME = new QName("http://srv.sd/", "deletePicture");
    private final static QName _DeleteAlbumResponse_QNAME = new QName("http://srv.sd/", "deleteAlbumResponse");
    private final static QName _ListAlbumsResponse_QNAME = new QName("http://srv.sd/", "listAlbumsResponse");
    private final static QName _DeleteAlbum_QNAME = new QName("http://srv.sd/", "deleteAlbum");
    private final static QName _GetPictureDataResponseReturn_QNAME = new QName("", "return");

    /**
     * Create a new ObjectFactory that can be used to create new instances of schema derived classes for package: sd.clt.ws
     * 
     */
    public ObjectFactory() {
    }

    /**
     * Create an instance of {@link DeletePictureResponse }
     * 
     */
    public DeletePictureResponse createDeletePictureResponse() {
        return new DeletePictureResponse();
    }

    /**
     * Create an instance of {@link ListPictures }
     * 
     */
    public ListPictures createListPictures() {
        return new ListPictures();
    }

    /**
     * Create an instance of {@link IOException }
     * 
     */
    public IOException createIOException() {
        return new IOException();
    }

    /**
     * Create an instance of {@link GetPictureData }
     * 
     */
    public GetPictureData createGetPictureData() {
        return new GetPictureData();
    }

    /**
     * Create an instance of {@link ListPicturesResponse }
     * 
     */
    public ListPicturesResponse createListPicturesResponse() {
        return new ListPicturesResponse();
    }

    /**
     * Create an instance of {@link CreateAlbum }
     * 
     */
    public CreateAlbum createCreateAlbum() {
        return new CreateAlbum();
    }

    /**
     * Create an instance of {@link CreateAlbumResponse }
     * 
     */
    public CreateAlbumResponse createCreateAlbumResponse() {
        return new CreateAlbumResponse();
    }

    /**
     * Create an instance of {@link GetPictureDataResponse }
     * 
     */
    public GetPictureDataResponse createGetPictureDataResponse() {
        return new GetPictureDataResponse();
    }

    /**
     * Create an instance of {@link ListAlbumsResponse }
     * 
     */
    public ListAlbumsResponse createListAlbumsResponse() {
        return new ListAlbumsResponse();
    }

    /**
     * Create an instance of {@link DeleteAlbum }
     * 
     */
    public DeleteAlbum createDeleteAlbum() {
        return new DeleteAlbum();
    }

    /**
     * Create an instance of {@link FileNotFoundException }
     * 
     */
    public FileNotFoundException createFileNotFoundException() {
        return new FileNotFoundException();
    }

    /**
     * Create an instance of {@link ListAlbums }
     * 
     */
    public ListAlbums createListAlbums() {
        return new ListAlbums();
    }

    /**
     * Create an instance of {@link DeleteAlbumResponse }
     * 
     */
    public DeleteAlbumResponse createDeleteAlbumResponse() {
        return new DeleteAlbumResponse();
    }

    /**
     * Create an instance of {@link DeletePicture }
     * 
     */
    public DeletePicture createDeletePicture() {
        return new DeletePicture();
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link ListPicturesResponse }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://srv.sd/", name = "listPicturesResponse")
    public JAXBElement<ListPicturesResponse> createListPicturesResponse(ListPicturesResponse value) {
        return new JAXBElement<ListPicturesResponse>(_ListPicturesResponse_QNAME, ListPicturesResponse.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link CreateAlbumResponse }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://srv.sd/", name = "createAlbumResponse")
    public JAXBElement<CreateAlbumResponse> createCreateAlbumResponse(CreateAlbumResponse value) {
        return new JAXBElement<CreateAlbumResponse>(_CreateAlbumResponse_QNAME, CreateAlbumResponse.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link GetPictureDataResponse }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://srv.sd/", name = "getPictureDataResponse")
    public JAXBElement<GetPictureDataResponse> createGetPictureDataResponse(GetPictureDataResponse value) {
        return new JAXBElement<GetPictureDataResponse>(_GetPictureDataResponse_QNAME, GetPictureDataResponse.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link CreateAlbum }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://srv.sd/", name = "createAlbum")
    public JAXBElement<CreateAlbum> createCreateAlbum(CreateAlbum value) {
        return new JAXBElement<CreateAlbum>(_CreateAlbum_QNAME, CreateAlbum.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link ListPictures }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://srv.sd/", name = "listPictures")
    public JAXBElement<ListPictures> createListPictures(ListPictures value) {
        return new JAXBElement<ListPictures>(_ListPictures_QNAME, ListPictures.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link DeletePictureResponse }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://srv.sd/", name = "deletePictureResponse")
    public JAXBElement<DeletePictureResponse> createDeletePictureResponse(DeletePictureResponse value) {
        return new JAXBElement<DeletePictureResponse>(_DeletePictureResponse_QNAME, DeletePictureResponse.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link GetPictureData }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://srv.sd/", name = "getPictureData")
    public JAXBElement<GetPictureData> createGetPictureData(GetPictureData value) {
        return new JAXBElement<GetPictureData>(_GetPictureData_QNAME, GetPictureData.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link IOException }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://srv.sd/", name = "IOException")
    public JAXBElement<IOException> createIOException(IOException value) {
        return new JAXBElement<IOException>(_IOException_QNAME, IOException.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link ListAlbums }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://srv.sd/", name = "listAlbums")
    public JAXBElement<ListAlbums> createListAlbums(ListAlbums value) {
        return new JAXBElement<ListAlbums>(_ListAlbums_QNAME, ListAlbums.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link FileNotFoundException }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://srv.sd/", name = "FileNotFoundException")
    public JAXBElement<FileNotFoundException> createFileNotFoundException(FileNotFoundException value) {
        return new JAXBElement<FileNotFoundException>(_FileNotFoundException_QNAME, FileNotFoundException.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link DeletePicture }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://srv.sd/", name = "deletePicture")
    public JAXBElement<DeletePicture> createDeletePicture(DeletePicture value) {
        return new JAXBElement<DeletePicture>(_DeletePicture_QNAME, DeletePicture.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link DeleteAlbumResponse }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://srv.sd/", name = "deleteAlbumResponse")
    public JAXBElement<DeleteAlbumResponse> createDeleteAlbumResponse(DeleteAlbumResponse value) {
        return new JAXBElement<DeleteAlbumResponse>(_DeleteAlbumResponse_QNAME, DeleteAlbumResponse.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link ListAlbumsResponse }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://srv.sd/", name = "listAlbumsResponse")
    public JAXBElement<ListAlbumsResponse> createListAlbumsResponse(ListAlbumsResponse value) {
        return new JAXBElement<ListAlbumsResponse>(_ListAlbumsResponse_QNAME, ListAlbumsResponse.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link DeleteAlbum }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://srv.sd/", name = "deleteAlbum")
    public JAXBElement<DeleteAlbum> createDeleteAlbum(DeleteAlbum value) {
        return new JAXBElement<DeleteAlbum>(_DeleteAlbum_QNAME, DeleteAlbum.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link byte[]}{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "", name = "return", scope = GetPictureDataResponse.class)
    public JAXBElement<byte[]> createGetPictureDataResponseReturn(byte[] value) {
        return new JAXBElement<byte[]>(_GetPictureDataResponseReturn_QNAME, byte[].class, GetPictureDataResponse.class, ((byte[]) value));
    }

}
