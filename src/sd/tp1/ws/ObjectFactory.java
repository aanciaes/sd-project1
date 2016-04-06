
package sd.tp1.ws;

import javax.xml.bind.JAXBElement;
import javax.xml.bind.annotation.XmlElementDecl;
import javax.xml.bind.annotation.XmlRegistry;
import javax.xml.namespace.QName;


/**
 * This object contains factory methods for each 
 * Java content interface and Java element interface 
 * generated in the sd.tp1.ws package. 
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

    private final static QName _CreateAlbum_QNAME = new QName("http://soap.srv.sd/", "createAlbum");
    private final static QName _CreateAlbumResponse_QNAME = new QName("http://soap.srv.sd/", "createAlbumResponse");
    private final static QName _GetPictureDataResponse_QNAME = new QName("http://soap.srv.sd/", "getPictureDataResponse");
    private final static QName _GetPictureData_QNAME = new QName("http://soap.srv.sd/", "getPictureData");
    private final static QName _GetListAlbunsResponse_QNAME = new QName("http://soap.srv.sd/", "getListAlbunsResponse");
    private final static QName _DeletePictureResponse_QNAME = new QName("http://soap.srv.sd/", "deletePictureResponse");
    private final static QName _GetListPictures_QNAME = new QName("http://soap.srv.sd/", "getListPictures");
    private final static QName _UploadPictureResponse_QNAME = new QName("http://soap.srv.sd/", "uploadPictureResponse");
    private final static QName _GetListPicturesResponse_QNAME = new QName("http://soap.srv.sd/", "getListPicturesResponse");
    private final static QName _DeleteAlbumResponse_QNAME = new QName("http://soap.srv.sd/", "deleteAlbumResponse");
    private final static QName _DeletePicture_QNAME = new QName("http://soap.srv.sd/", "deletePicture");
    private final static QName _UploadPicture_QNAME = new QName("http://soap.srv.sd/", "uploadPicture");
    private final static QName _GetListAlbuns_QNAME = new QName("http://soap.srv.sd/", "getListAlbuns");
    private final static QName _DeleteAlbum_QNAME = new QName("http://soap.srv.sd/", "deleteAlbum");
    private final static QName _UploadPictureArg2_QNAME = new QName("", "arg2");
    private final static QName _GetPictureDataResponseReturn_QNAME = new QName("", "return");

    /**
     * Create a new ObjectFactory that can be used to create new instances of schema derived classes for package: sd.tp1.ws
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
     * Create an instance of {@link GetListAlbunsResponse }
     * 
     */
    public GetListAlbunsResponse createGetListAlbunsResponse() {
        return new GetListAlbunsResponse();
    }

    /**
     * Create an instance of {@link GetPictureData }
     * 
     */
    public GetPictureData createGetPictureData() {
        return new GetPictureData();
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
     * Create an instance of {@link DeleteAlbum }
     * 
     */
    public DeleteAlbum createDeleteAlbum() {
        return new DeleteAlbum();
    }

    /**
     * Create an instance of {@link GetListAlbuns }
     * 
     */
    public GetListAlbuns createGetListAlbuns() {
        return new GetListAlbuns();
    }

    /**
     * Create an instance of {@link UploadPicture }
     * 
     */
    public UploadPicture createUploadPicture() {
        return new UploadPicture();
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
     * Create an instance of {@link UploadPictureResponse }
     * 
     */
    public UploadPictureResponse createUploadPictureResponse() {
        return new UploadPictureResponse();
    }

    /**
     * Create an instance of {@link GetListPictures }
     * 
     */
    public GetListPictures createGetListPictures() {
        return new GetListPictures();
    }

    /**
     * Create an instance of {@link GetListPicturesResponse }
     * 
     */
    public GetListPicturesResponse createGetListPicturesResponse() {
        return new GetListPicturesResponse();
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link CreateAlbum }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://soap.srv.sd/", name = "createAlbum")
    public JAXBElement<CreateAlbum> createCreateAlbum(CreateAlbum value) {
        return new JAXBElement<CreateAlbum>(_CreateAlbum_QNAME, CreateAlbum.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link CreateAlbumResponse }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://soap.srv.sd/", name = "createAlbumResponse")
    public JAXBElement<CreateAlbumResponse> createCreateAlbumResponse(CreateAlbumResponse value) {
        return new JAXBElement<CreateAlbumResponse>(_CreateAlbumResponse_QNAME, CreateAlbumResponse.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link GetPictureDataResponse }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://soap.srv.sd/", name = "getPictureDataResponse")
    public JAXBElement<GetPictureDataResponse> createGetPictureDataResponse(GetPictureDataResponse value) {
        return new JAXBElement<GetPictureDataResponse>(_GetPictureDataResponse_QNAME, GetPictureDataResponse.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link GetPictureData }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://soap.srv.sd/", name = "getPictureData")
    public JAXBElement<GetPictureData> createGetPictureData(GetPictureData value) {
        return new JAXBElement<GetPictureData>(_GetPictureData_QNAME, GetPictureData.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link GetListAlbunsResponse }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://soap.srv.sd/", name = "getListAlbunsResponse")
    public JAXBElement<GetListAlbunsResponse> createGetListAlbunsResponse(GetListAlbunsResponse value) {
        return new JAXBElement<GetListAlbunsResponse>(_GetListAlbunsResponse_QNAME, GetListAlbunsResponse.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link DeletePictureResponse }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://soap.srv.sd/", name = "deletePictureResponse")
    public JAXBElement<DeletePictureResponse> createDeletePictureResponse(DeletePictureResponse value) {
        return new JAXBElement<DeletePictureResponse>(_DeletePictureResponse_QNAME, DeletePictureResponse.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link GetListPictures }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://soap.srv.sd/", name = "getListPictures")
    public JAXBElement<GetListPictures> createGetListPictures(GetListPictures value) {
        return new JAXBElement<GetListPictures>(_GetListPictures_QNAME, GetListPictures.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link UploadPictureResponse }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://soap.srv.sd/", name = "uploadPictureResponse")
    public JAXBElement<UploadPictureResponse> createUploadPictureResponse(UploadPictureResponse value) {
        return new JAXBElement<UploadPictureResponse>(_UploadPictureResponse_QNAME, UploadPictureResponse.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link GetListPicturesResponse }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://soap.srv.sd/", name = "getListPicturesResponse")
    public JAXBElement<GetListPicturesResponse> createGetListPicturesResponse(GetListPicturesResponse value) {
        return new JAXBElement<GetListPicturesResponse>(_GetListPicturesResponse_QNAME, GetListPicturesResponse.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link DeleteAlbumResponse }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://soap.srv.sd/", name = "deleteAlbumResponse")
    public JAXBElement<DeleteAlbumResponse> createDeleteAlbumResponse(DeleteAlbumResponse value) {
        return new JAXBElement<DeleteAlbumResponse>(_DeleteAlbumResponse_QNAME, DeleteAlbumResponse.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link DeletePicture }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://soap.srv.sd/", name = "deletePicture")
    public JAXBElement<DeletePicture> createDeletePicture(DeletePicture value) {
        return new JAXBElement<DeletePicture>(_DeletePicture_QNAME, DeletePicture.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link UploadPicture }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://soap.srv.sd/", name = "uploadPicture")
    public JAXBElement<UploadPicture> createUploadPicture(UploadPicture value) {
        return new JAXBElement<UploadPicture>(_UploadPicture_QNAME, UploadPicture.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link GetListAlbuns }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://soap.srv.sd/", name = "getListAlbuns")
    public JAXBElement<GetListAlbuns> createGetListAlbuns(GetListAlbuns value) {
        return new JAXBElement<GetListAlbuns>(_GetListAlbuns_QNAME, GetListAlbuns.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link DeleteAlbum }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://soap.srv.sd/", name = "deleteAlbum")
    public JAXBElement<DeleteAlbum> createDeleteAlbum(DeleteAlbum value) {
        return new JAXBElement<DeleteAlbum>(_DeleteAlbum_QNAME, DeleteAlbum.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link byte[]}{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "", name = "arg2", scope = UploadPicture.class)
    public JAXBElement<byte[]> createUploadPictureArg2(byte[] value) {
        return new JAXBElement<byte[]>(_UploadPictureArg2_QNAME, byte[].class, UploadPicture.class, ((byte[]) value));
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
