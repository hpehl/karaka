package name.pehl.tire.server.project.control;

import javax.inject.Inject;

import name.pehl.tire.server.client.control.ClientConverter;
import name.pehl.tire.server.client.control.ClientRepository;
import name.pehl.tire.server.converter.AbstractEntityConverter;
import name.pehl.tire.server.converter.EntityConverter;

import com.googlecode.objectify.Key;

public class ProjectConverter extends
        AbstractEntityConverter<name.pehl.tire.server.project.entity.Project, name.pehl.tire.shared.model.Project>
        implements EntityConverter<name.pehl.tire.server.project.entity.Project, name.pehl.tire.shared.model.Project>
{
    @Inject ClientConverter clientConverter;
    @Inject ClientRepository clientRepository;


    @Override
    public name.pehl.tire.shared.model.Project toModel(name.pehl.tire.server.project.entity.Project entity)
    {
        assertEntity(entity);
        name.pehl.tire.shared.model.Project model = new name.pehl.tire.shared.model.Project(websafeKey(
                name.pehl.tire.server.project.entity.Project.class, entity), entity.getName());
        model.setDescription(entity.getDescription());
        return model;
    }


    @Override
    public name.pehl.tire.server.project.entity.Project fromModel(name.pehl.tire.shared.model.Project model)
    {
        assertModel(model);
        name.pehl.tire.server.project.entity.Project entity = new name.pehl.tire.server.project.entity.Project(
                model.getName(), model.getDescription());
        internalModelToEntity(model, entity);
        return entity;
    }


    @Override
    public void merge(name.pehl.tire.shared.model.Project model, name.pehl.tire.server.project.entity.Project entity)
    {
        assertNonTransientModel(model);
        entity.setName(model.getName());
        entity.setDescription(model.getDescription());
        internalModelToEntity(model, entity);
    }


    private void internalModelToEntity(name.pehl.tire.shared.model.Project model,
            name.pehl.tire.server.project.entity.Project entity)
    {
        // relations
        Key<name.pehl.tire.server.client.entity.Client> clientKey = null;
        if (model.getClient() != null)
        {
            if (model.getClient().isTransient())
            {
                name.pehl.tire.server.client.entity.Client newClientEntity = clientConverter.fromModel(model
                        .getClient());
                clientKey = clientRepository.put(newClientEntity);
            }
            else
            {
                clientKey = Key.<name.pehl.tire.server.client.entity.Client> create(model.getClient().getId());
            }
        }
        entity.setClient(clientKey);
    }
}
