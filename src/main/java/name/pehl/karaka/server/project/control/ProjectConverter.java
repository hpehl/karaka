package name.pehl.karaka.server.project.control;

import com.googlecode.objectify.Key;
import com.googlecode.objectify.Ref;
import name.pehl.karaka.server.client.control.ClientConverter;
import name.pehl.karaka.server.client.control.ClientRepository;
import name.pehl.karaka.server.client.entity.Client;
import name.pehl.karaka.server.converter.AbstractEntityConverter;
import name.pehl.karaka.server.converter.EntityConverter;

import javax.inject.Inject;

public class ProjectConverter extends
        AbstractEntityConverter<name.pehl.karaka.server.project.entity.Project, name.pehl.karaka.shared.model.Project>
        implements EntityConverter<name.pehl.karaka.server.project.entity.Project, name.pehl.karaka.shared.model.Project>
{
    @Inject ClientConverter clientConverter;
    @Inject ClientRepository clientRepository;


    @Override
    public name.pehl.karaka.shared.model.Project toModel(name.pehl.karaka.server.project.entity.Project entity)
    {
        assertEntity(entity);
        name.pehl.karaka.shared.model.Project model = new name.pehl.karaka.shared.model.Project(websafeKey(
                name.pehl.karaka.server.project.entity.Project.class, entity), entity.getName());
        model.setDescription(entity.getDescription());

        // client relation
        if (entity.getClient() != null && entity.getClient().get() != null)
        {
            model.setClient(clientConverter.toModel(entity.getClient().get()));
        }
        return model;
    }


    @Override
    public name.pehl.karaka.server.project.entity.Project fromModel(name.pehl.karaka.shared.model.Project model)
    {
        assertModel(model);
        name.pehl.karaka.server.project.entity.Project entity = new name.pehl.karaka.server.project.entity.Project(
                model.getName(), model.getDescription());
        internalModelToEntity(model, entity);
        return entity;
    }


    @Override
    public void merge(name.pehl.karaka.shared.model.Project model, name.pehl.karaka.server.project.entity.Project entity)
    {
        assertNonTransientModel(model);
        entity.setName(model.getName());
        entity.setDescription(model.getDescription());
        internalModelToEntity(model, entity);
    }


    private void internalModelToEntity(name.pehl.karaka.shared.model.Project model,
            name.pehl.karaka.server.project.entity.Project entity)
    {
        // relations
        Key<name.pehl.karaka.server.client.entity.Client> clientKey = null;
        if (model.getClient() != null)
        {
            if (model.getClient().isTransient())
            {
                name.pehl.karaka.server.client.entity.Client newClientEntity = clientConverter.fromModel(model
                        .getClient());
                Client savedClient = clientRepository.save(newClientEntity);
                entity.setClient(Ref.create(savedClient));
            }
            else
            {
                clientKey = Key.create(model.getClient().getId());
                entity.setClient(Ref.create(clientKey));
            }
        }
        else
        {
            entity.setClient(null);
        }
    }
}
